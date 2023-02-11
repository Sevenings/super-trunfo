import gameobjects.*;
import jogador.*;

public class Main {
    public static void main(String[] args) {
        Baralho super_trunfo = new Baralho();
        super_trunfo.carregar("pokemon");

        //super_trunfo.listarCartas();

        JogadorHumano player = new JogadorHumano("Luffy");
        player.receberCarta(super_trunfo.pegarDoTopo());
        player.escolheAtributo();
    }
}
