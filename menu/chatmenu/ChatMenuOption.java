package menu.chatmenu;

import menu.*;

public class ChatMenuOption extends Option {
    public ChatMenuOption(String nome, Object content) {
        this.nome = nome;
        this.content = content;
    }

    @Override
    public Object select(Menu selector) {
        return content;
    }
}
