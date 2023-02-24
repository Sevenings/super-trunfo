import gameobjects.*;
import jogador.*;
import jogo.*;
import java.util.*;
import musica.*;

public class Main {
    public static void main(String[] args) {
	Musicao.RunMusic("musica/pula.wav");
	
        Jogo jogo = new Jogo();
        MenuInicial menu = new MenuInicial(jogo);
        menu.open();
    }
}
