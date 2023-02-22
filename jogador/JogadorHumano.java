package jogador;

import gameobjects.*;
import menu.*;
import menu.chatmenu.*;
import java.util.*;

public class JogadorHumano extends Jogador {
    public JogadorHumano(String nome) {
       super(nome);
    }

    @Override
    public Atributo escolheAtributo() {
        Carta cartaDaMao = getCartaDaMao();
        Menu menu = new ChatMenu(cartaDaMao.getTitulo());
        int i = 1;
        for (Atributo atributo : cartaDaMao.getListaAtributos()) {
            List<String> keywords = new ArrayList<String>();
            keywords.add(atributo.getNome());
            keywords.add(Integer.toString(i)); i++;
            menu.add(new ChatMenuOption(atributo.toString(), atributo, keywords));
        }
        return (Atributo) menu.open();
    }
}
