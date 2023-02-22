package jogo;

import java.util.*;
import jogador.*;
import gameobjects.*;
import prettyprint.PrettyPrint;

public class Jogo {
    private String tema;
    private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
    private Jogador vez;
    private Jogador vencedor = null;
    private int total_de_cartas;
    private Baralho baralho = new Baralho();
    private int delay = 500;

    public Jogador play() throws ThemeNotFoundException {
        MenuInicial.limparTela();
        prepararJogo();
        escolherQuemComeca(jogadores);
        gameLoop();
        return vencedor;
    }

    protected void escolherQuemComeca(List<Jogador> jogadores) {
        // Deixar protected pois abre margem para que, no futuro,
        // uma classe herde de Jogo e dê override, colocando aqui
        // uma outra forma de escolher o primeiro jogador, como
        // uma disputa de par ou ímpar, ou jokenpô, retornando o 
        // vencedor do jogo.
        Random roleta = new Random();
        int n_jogadores = jogadores.size();
        int index_jogador_escolhido = roleta.nextInt(n_jogadores);
        Jogador escolhido = jogadores.get(index_jogador_escolhido);
        this.vez = escolhido;
        animaçãoEscolher(index_jogador_escolhido);
    }

    protected void animaçãoEscolher(int index_jogador_escolhido) {
        timedPrint("Vamos escolher quem começa!", 4);
        String seta;
        int n_de_oscilações = 40; //Precisa ser Par
        for (int i = 0; i < n_de_oscilações + index_jogador_escolhido; i++) {
            MenuInicial.limparTela();
            if (i%2 == 1) {
                seta = "<--";
            } else {
                seta = "-->";
            }
            String text = jogadores.get(0).getNome() + " " + seta + " " +  jogadores.get(1).getNome();
            System.out.println(text);
            PrettyPrint.delay(4*(i+1)^2);
        }

        timedPrint(this.vez.getNome() + " começa!", 4);
    }

    protected void prepararJogo() throws ThemeNotFoundException {
        carregarTema();
        // Distribuir as cartas
        vencedor = null;
        baralho.embaralhar();
        baralho.distribuir(jogadores);
    }

    private void carregarTema() throws ThemeNotFoundException {
        try {
            baralho.carregar(tema);
        } catch (ThemeNotFoundException tnfe) {
            throw tnfe;
        }
    }

    protected void verificarVencedor() {
        for (Jogador jogador : jogadores) {
            if (jogador.quantasCartasNaMao() == total_de_cartas) {
                this.vencedor = jogador;
                return;
            }
        }
    }

    protected void ganhaRodada(Jogador venceu, Jogador perdeu) {
        timedPrint(venceu.getNome() + " venceu!");
        delay();
        this.vez = venceu;
        venceu.receberCarta(venceu.pegaDoMonte()); // Coloca a própria carta no fim do baralho
        venceu.receberCarta(perdeu.pegaDoMonte()); // Coloca a carta do oponente no fim do baralho
    }

    private void delay(double multiplier) {
        PrettyPrint.delay((int) (delay*multiplier));
    }

    private void delay() {
        delay(1);
    }

    private void timedPrint(String string, double multiplier) {
        PrettyPrint.timedPrint(string, (int) (delay*multiplier));
    }

    private void timedPrint(String string) {
        timedPrint(string, 1);
    }

    protected void gameLoop() {
        for (; vencedor == null; verificarVencedor()) {
            MenuInicial.limparTela();
            for (Jogador jogador : jogadores) {
                System.out.println("Cartas no baralho de " + jogador.getNome() + ": " + jogador.quantasCartasNaMao());
            }

            delay(2);

            timedPrint(vez.getNome() + " vai escolher um atributo");
            Atributo atributo_escolhido = vez.escolheAtributo();    // O Jogador da Vez escolhe o Atributo 
            timedPrint(vez.getNome() + " escolheu: " + atributo_escolhido.getNome());
            delay();

            Jogador jogadorComTrunfo = alguémTemTrunfo(jogadores);
            ArrayList<Jogador> outrosJogadores = (ArrayList<Jogador>) jogadores.clone();

            Atributo atr1 = jogadores.get(0).getAtributoEquivalente(atributo_escolhido);    
            Atributo atr2 = jogadores.get(1).getAtributoEquivalente(atributo_escolhido);

            timedPrint("Atributos jogados:");
            timedPrint(jogadores.get(0).getNome() + " jogou: " + atr1.toString());
            timedPrint(jogadores.get(1).getNome() + " jogou: " + atr2.toString());
            delay();

            if (jogadorComTrunfo != null && alguémTemCartaA(outrosJogadores) == null) {
                timedPrint("Preparem-se! " + jogadorComTrunfo.getNome() + " joga o SUPER TRUNFO!");
                delay();
                calculoAtributosComTrunfo(jogadorComTrunfo, outrosJogadores.get(0));
            } else {
                calculoAtributosSemTrunfo(atr1, atr2);
            }
        }
    }

    protected void calculoAtributosSemTrunfo(Atributo atr1, Atributo atr2) {
        if (atr1.compareTo(atr2) > 0) {
            ganhaRodada(jogadores.get(0), jogadores.get(1));
        } else if (atr2.compareTo(atr1) > 0) {
            ganhaRodada(jogadores.get(1), jogadores.get(0));
        } else {
            Random roleta = new Random();
            int vencedor = roleta.nextInt(1);
            int perdedor = (1+vencedor)%2;
            ganhaRodada(jogadores.get(vencedor), jogadores.get(perdedor));
        }
    }

    protected void calculoAtributosComTrunfo(Jogador jogadorComTrunfo, Jogador outroJogador) {
        ganhaRodada(jogadorComTrunfo, outroJogador);
    }

    protected void setVencedor(Jogador jogador) {
        this.vencedor = jogador;
    }

    protected Jogador alguémTemTrunfo(List<Jogador> jogadores) {
        for (Jogador jogador : jogadores) {
            if (jogador.getCartaDaMao().isTrunfo()) {
                return jogador;
            }
        }
        return null;
    }

    protected Jogador alguémTemCartaA(List<Jogador> jogadores) {
        for (Jogador jogador : jogadores) {
            if (jogador.getCartaDaMao().getId().contains("A"))
                    return jogador;
        }
        return null;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTema() {
        return tema;
    }

    public void addJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

}
