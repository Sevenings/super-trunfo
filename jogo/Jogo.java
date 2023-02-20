package jogo;

import java.util.*;
import jogador.*;
import gameobjects.*;

public class Jogo {
    private List<Jogador> jogadores;
    private Jogador vez;
    private Jogador vencedor = null;
    private int total_de_cartas;
    private Baralho baralho = new Baralho();
    private int delay = 500;

    public Jogo(List<Jogador> jogadores, String tema) {
        this.jogadores = jogadores;
        try {
            this.baralho.carregar(tema);
        } catch (ThemeNotFoundException tnfe) {
            tnfe.printStackTrace();
        }
    }

    public Jogador play() {
        escolherQuemComeca(jogadores);
        prepararJogo();
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
        Jogador escolhido = jogadores.get(roleta.nextInt(n_jogadores));
        this.vez = escolhido;
        System.out.println(this.vez.getNome() + " começa!");
        System.out.println("------------------");
        delay();
    }

    protected void prepararJogo() {
        // Distribuir as cartas
        vencedor = null;
        baralho.embaralhar();
        baralho.distribuir(jogadores);
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
        System.out.println(venceu.getNome() + " venceu!");
        this.vez = venceu;
        venceu.receberCarta(venceu.pegaDoMonte()); // Coloca a própria carta no fim do baralho
        venceu.receberCarta(perdeu.pegaDoMonte()); // Coloca a carta do oponente no fim do baralho
    }

    private void delay() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    protected void gameLoop() {
        for (;vencedor == null; verificarVencedor()) {
            for (Jogador jogador : jogadores) {
                System.out.println("Cartas no baralho de " + jogador.getNome() + ": " + jogador.quantasCartasNaMao());
            }
            System.out.println("------------------");
            delay();

            // O Jogador da Vez escolhe o Atributo
            Atributo atributo_escolhido = vez.escolheAtributo();
            System.out.println(vez.getNome() + " escolheu: " + atributo_escolhido.getNome());
            delay();

            // Pega o atributo correspondente de todos os jogadores
            Atributo atr1 = jogadores.get(0).getAtributoEquivalente(atributo_escolhido);
            Atributo atr2 = jogadores.get(1).getAtributoEquivalente(atributo_escolhido);

            System.out.println("Atributos jogados:");
            System.out.println(atr1.toString());
            System.out.println(atr2.toString());

            System.out.println(atr1.compareTo(atr2));
            if (atr1.compareTo(atr2) > 1) {
                ganhaRodada(jogadores.get(0), jogadores.get(1));
            } else if (atr2.compareTo(atr1) > 1) {
                ganhaRodada(jogadores.get(1), jogadores.get(0));
            } else {
                Random roleta = new Random();
                int vencedor = roleta.nextInt(1);
                int perdedor = (1+vencedor)%2;
                ganhaRodada(jogadores.get(vencedor), jogadores.get(perdedor));
            }
            /*
            ArrayList<Atributo> listaAtributos = new ArrayList<Atributo>();
            for (Jogador jogador : jogadores) {
                listaAtributos.add(jogador.getAtributoEquivalente(atributo_escolhido));
            }
            for (Atributo atributo : listaAtributos) {
                System.out.println(atributo.toString());
            }
            */
        }
    }

    protected void setVencedor(Jogador jogador) {
        this.vencedor = jogador;
    }

}
