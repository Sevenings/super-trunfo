package jogador;

import gameobjects.*;
import java.util.*;

public class Bot extends Jogador {
    public Bot(String nome) {
        super(nome);
    }

    public Atributo escolheAtributo() {
        Random roleta = new Random();

        Carta cartaDaMao = this.getCartaDaMao();
        List<Atributo> listaAtributos = cartaDaMao.getListaAtributos();
        int numeroDeAtributos = listaAtributos.size();
        int nDoAtributoEscolhido = roleta.nextInt(numeroDeAtributos);
        Atributo atributoEscolhido = listaAtributos.get(nDoAtributoEscolhido);
        return atributoEscolhido;
    }
}


