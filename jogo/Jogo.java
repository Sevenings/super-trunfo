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

    public Jogador play() throws ThemeNotFoundException {   // A principal função da classe, por onde todo o jogo é executado
        MenuInicial.limparTela();
        prepararJogo();
        escolherQuemComeca(jogadores);
        gameLoop();
        return vencedor;
    }

    public void reset() {
        jogadores.clear();
        baralho.clear();
    }

    protected void escolherQuemComeca(List<Jogador> jogadores) {    
        //Método que determina de maneira aleatória quem será o primeiro,
        //determinando então qual jogador está na variável "vez"

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
        animaçãoEscolherQuemComeça(index_jogador_escolhido);
    }

    protected void animaçãoEscolherQuemComeça(int index_jogador_escolhido) {
        //Método que pega o indice do jogador escolhido na Lista "jogadores"
        //e faz uma animação de roleta, apontando para o jogador escolhido
        timedPrint("Vamos escolher quem começa!", 4);
        String seta;
        int n_de_oscilações = 20; 
        for (int i = 0; i < 2*n_de_oscilações + index_jogador_escolhido; i++) {
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
        // Prepara o jogo:
        // carrega o tema do baralho, embaralha, 
        // distribui as cartas e limpa a variável "vencedor"

        carregarTema();
        baralho.embaralhar();
        baralho.distribuir(jogadores);
        vencedor = null;
    }

    private void carregarTema() throws ThemeNotFoundException {
        // Carrega o tema do baralho
        try {
            baralho.carregar(tema);
        } catch (ThemeNotFoundException tnfe) {
            throw tnfe;
        }
    }

    protected void verificarVencedor() {
        // Método que verifica se há vencedor,
        // esse faz com que o primeiro jogador 
        // a ter todas as cartas na mão seja o vencedor
        // É executado todo final de loop do método "gameLoop()"
        for (Jogador jogador : jogadores) {
            if (jogador.quantasCartasNaMao() == total_de_cartas) {
                this.vencedor = jogador;
                return;
            }
        }
    }

    protected void ganhaRodada(Jogador venceu, Jogador perdeu) {
        // Método que faz o jogador que "venceu" a rodada pegar a carta do jogador "perdeu"
        // e colocar, junto com sua própria carta, no final do baralho
        timedPrint(venceu.getNome() + " venceu!", 2);
        this.vez = venceu;
        venceu.receberCarta(venceu.pegaDoMonte()); // Coloca a própria carta no fim do baralho
        venceu.receberCarta(perdeu.pegaDoMonte()); // Coloca a carta do oponente no fim do baralho
    }

    private void delay(double multiplier) {
        // Adaptação do método PrettyPrint.delay()
        // pausa a execução do código por alguns segundos
        // que ficam dependentes do atributo "delay" da classe Jogo
        PrettyPrint.delay((int) (delay*multiplier));
    }

    private void delay() {
        // Sobrecarga do delay acima
        delay(1);
    }

    private void timedPrint(String string, double multiplier) {
        // Adaptação do PrettyPrint.timedPrint()
        PrettyPrint.timedPrint(string, (int) (delay*multiplier));
    }

    private void timedPrint(String string) {
        // Sobrercarga do timedPrint acima
        timedPrint(string, 1);
    }

    protected void mostrarStatus() {
        // Printa quantas cartas no baralho dos jogadores
        for (Jogador jogador : jogadores) {
            System.out.println("Cartas no baralho de " + jogador.getNome() + ": " + jogador.quantasCartasNaMao());
        }

        delay(2);
    }

    protected void gameLoop() {
        // Método onde é organizada a lógica do jogo
        // O loop "for" vai rodar até que exista algum vencedor
        // Final de cada loop ele verifica se há algum vencedor
        for (; vencedor == null; verificarVencedor()) {
            MenuInicial.limparTela();

            mostrarStatus();

            timedPrint(vez.getNome() + " vai escolher um atributo");
            Atributo atributo_escolhido = vez.escolheAtributo();    // O Jogador da Vez escolhe o Atributo 
            timedPrint(vez.getNome() + " escolheu: " + atributo_escolhido.getNome());
            delay();

            Jogador jogadorComTrunfo = alguémTemTrunfo(jogadores);
            ArrayList<Jogador> outrosJogadores = (ArrayList<Jogador>) jogadores.clone();

            Atributo atr1 = jogadores.get(0).getAtributoEquivalente(atributo_escolhido);    
            Atributo atr2 = jogadores.get(1).getAtributoEquivalente(atributo_escolhido);

            mostrarAtributosJogados(atr1, atr2);

            if (jogadorComTrunfo != null && alguémTemCartaA(outrosJogadores) == null) {
                timedPrint("Preparem-se! " + jogadorComTrunfo.getNome() + " joga o SUPER TRUNFO!");
                delay();
                calculoAtributosComTrunfo(jogadorComTrunfo, outrosJogadores.get(0));
            } else {
                calculoAtributosSemTrunfo(atr1, atr2);
            }
        }
    }

    private void mostrarAtributosJogados(Atributo atr1, Atributo atr2) {
        // Printa quais atributos e cartas cada player tá jogando
        Carta cartaP1 = jogadores.get(0).getCartaDaMao();
        Carta cartaP2 = jogadores.get(1).getCartaDaMao();
        timedPrint("Atributos jogados:");
        timedPrint(jogadores.get(0).getNome() + " jogou: " + cartaP1.getTitulo() + " " + atr1.toString());
        timedPrint(jogadores.get(1).getNome() + " jogou: " + cartaP2.getTitulo() + " " + atr2.toString());
    }

    protected void calculoAtributosSemTrunfo(Atributo atr1, Atributo atr2) {
        // Método que determina qual player ganhou a rodada baseado nos atributos
        // jogados, o atr1 é o atributo jogado pelo player1, atr2 pelo player2
        // É a regra aplicada no caso em que NÃO se usa a regra do trunfo 
        // (também é válido quando temos trunfo + carta A)
        if (atr1.compareTo(atr2) > 0) {
            ganhaRodada(jogadores.get(0), jogadores.get(1));
        } else if (atr2.compareTo(atr1) > 0) {
            ganhaRodada(jogadores.get(1), jogadores.get(0));
        } else {
            Random roleta = new Random();
            int vencedor = roleta.nextInt(2);
            int perdedor = (1+vencedor)%2;
            ganhaRodada(jogadores.get(vencedor), jogadores.get(perdedor));
        }
    }

    protected void calculoAtributosComTrunfo(Jogador jogadorComTrunfo, Jogador outroJogador) {
        // Método que aplica a regra de vitória quando alguém tem o trunfo
        // e o outro jogador não tem carta A
        ganhaRodada(jogadorComTrunfo, outroJogador);
    }

    protected void setVencedor(Jogador jogador) {
        this.vencedor = jogador;
    }

    public Jogador getVencedor() {
        return vencedor;
    }

    protected Jogador alguémTemTrunfo(List<Jogador> jogadores) {
        // Verifica qual jogador tem o Trunfo, se nenhum tiver
        // retorna null
        for (Jogador jogador : jogadores) {
            if (jogador.getCartaDaMao().isTrunfo()) {
                return jogador;
            }
        }
        return null;
    }

    protected Jogador alguémTemCartaA(List<Jogador> jogadores) {
        // Verifica se algum jogador tem uma Carta A
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
