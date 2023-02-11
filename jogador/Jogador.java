package jogador;

import java.lang.Comparable;
import gameobjects.*;

public abstract class Jogador {
    private String nome;
    private Baralho monte = new Baralho();
    private Atributo atributoEscolhido;
    
    public Jogador(String nome) {
        this.nome = nome;
    }

    public void receberCarta(Carta carta) {
        this.monte.addLast(carta);
    }

    public Carta pegaDoMonte() {
        return this.monte.pollFirst();
    }

    public void colocarNoMonte(Carta carta) {
        this.monte.addLast(carta);
    }

    public Baralho getMonte() {
        return this.monte;
    }

    public int quantasCartasNaMao() {
        return this.getMonte().size();
    }

    public Carta getCartaDaMao() {
        return this.monte.getFirst();
    }

    public abstract Atributo escolheAtributo();

}
