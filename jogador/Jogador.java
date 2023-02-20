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

    public Baralho getMonte() {
        return this.monte;
    }

    public int quantasCartasNaMao() {
        return this.getMonte().size();
    }

    public Carta getCartaDaMao() {
        return this.monte.getFirst();
    }

    public Atributo getAtributoEquivalente(Atributo atributoBase) {
        Carta cartaDaMao = this.getCartaDaMao();
        for (Atributo atributo : cartaDaMao.getListaAtributos()) {
            if (atributo.getNome().equals(atributoBase.getNome())) {
                return atributo;
            }
        }
        return null;
    }

    public String getNome() {
        return this.nome;
    }

    public abstract Atributo escolheAtributo();

}
