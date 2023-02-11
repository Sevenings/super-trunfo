import gameobjects.*;
import jogador.*;

public class Main {
    public static void main(String[] args) {
        Baralho super_trunfo = new Baralho();
        try {
            super_trunfo.carregar("carros");
        } catch (ThemeNotFoundException tnfe) {
            tnfe.printStackTrace();
        }

        JogadorHumano player = new JogadorHumano("Luffy");
        player.receberCarta(super_trunfo.pegarDoTopo());
        player.escolheAtributo();
    }
}
