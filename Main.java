import gameobjects.*;
import jogador.*;
import jogo.*;
import java.util.*;
import musica.*;

public class Main {
    public static void main(String[] args) {
        String tema = "carros";

        //Musicao.RunMusic("musica/btr.wav");

        JogadorHumano player = new JogadorHumano("Luffy");
        Bot robo = new Bot("Senku-300");

        ArrayList<Jogador> jogadores = new ArrayList();
        jogadores.add(player);
        jogadores.add(robo);

        Jogo jogo = new Jogo(jogadores);
        MenuInicial menu = new MenuInicial(jogo);
        menu.open();
    }
}
