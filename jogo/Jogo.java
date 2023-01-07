package jogo;

import java.util.*;
import jogador.*;

public class Jogo {
    private List<Jogador> jogadores;
    private Jogador vez;
    private Jogador vencedor;

    public Jogo(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public Jogador play() {
        Random random = new Random();
        vez = jogadores.get(random.nextInt(jogadores.size())); 

        return vencedor;
    }
}
