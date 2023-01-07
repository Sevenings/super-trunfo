package jogador;

import java.lang.Comparable;
import gameobjects.*;

public abstract class Jogador implements Comparable<Jogador> {
    private String nome;
    private Baralho monte = new Baralho();
    private Carta cartaNaMao;
    
    public Jogador(String nome) {
        this.nome = nome;
    }

    public void receberCarta(Carta carta) {
        this.monte.addLast(carta);
    }

    public Carta pegaDoMonte() {
        return this.monte.pollFirst();
    }

    public Baralho getMonte() {
        return this.monte;
    }

    public abstract String escolheAtributo();

}
