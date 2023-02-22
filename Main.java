import gameobjects.*;
import jogador.*;
import jogo.*;
import java.util.*;
import musica.*;

public class Main {
    public static void main(String[] args) {
        JogadorHumano player = new JogadorHumano("Luffy");
        Bot robo = new Bot("Senku-300 (BOT)");

        Jogo jogo = new Jogo();
        MenuInicial menu = new MenuInicial(jogo);
        menu.open();
    }
}
