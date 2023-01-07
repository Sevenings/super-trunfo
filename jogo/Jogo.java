package jogo;

import java.util.*;
import jogador.*;

public class Jogo {
    private List<Jogador> jogadores;

    public Jogo(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public Jogador play() {
        return vencedor;
    }
}
