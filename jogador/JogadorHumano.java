package jogador;

import gameobjects.*;
import menu.*;
import menu.chatmenu.*;

public class JogadorHumano extends Jogador {
    public JogadorHumano(String nome) {
       super(nome);
    }

    @Override
    public Atributo escolheAtributo() {
        Carta cartaDaMao = getCartaDaMao();
        Menu menu = new ChatMenu(cartaDaMao.getNome());
        for (Atributo atributo : cartaDaMao.getListaAtributos()) {
            menu.add(new ChatMenuOption(atributo.toString(), atributo));
        }
        return (Atributo) menu.open();
    }
}
