import gameobjects.*;
import jogador.*;
import jogo.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String tema = "carros";

        JogadorHumano player = new JogadorHumano("Luffy");
        Bot robo = new Bot("Senku-300");

        List<Jogador> jogadores = new ArrayList();
        jogadores.add(player);
        jogadores.add(robo);

        Jogo jogo = new Jogo(jogadores, tema);
        jogo.play();
    }
}
