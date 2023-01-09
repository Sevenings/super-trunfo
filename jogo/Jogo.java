package jogo;

import java.util.*;
import jogador.*;
import gameobjects.Baralho;

public class Jogo {
    private List<Jogador> jogadores;
    private Jogador vez;
    private Jogador vencedor = null;
    private int total_de_cartas;
    private Baralho baralho = new Baralho();

    public Jogo(List<Jogador> jogadores, String tema) {
        this.jogadores = jogadores;
        this.baralho.carregar(tema);
    }

    public Jogador play() {
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
        Jogador escolhido = jogadores.get(roleta.nextInt(n_jogadores));
        this.vez = escolhido;
    }

    protected void verificarVencedor() {
        for (Jogador jogador : jogadores) {
            if (jogador.quantasCartasNaMao() == total_de_cartas) {
                this.vencedor = jogador;
                return;
            }
        }
    }

    protected void gameLoop() {
        for (;vencedor == null; verificarVencedor()) {
           // Colocar o corpo do jogo  
        }
    }

}
