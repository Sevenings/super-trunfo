import gameobjects.*;

public class Main {
    public static void main(String[] args) {
        Baralho super_trunfo = new Baralho();
        try {
            super_trunfo.carregar("carros");
        } catch (ThemeNotFoundException tnfe) {
            tnfe.printStackTrace();
        }

        super_trunfo.listarCartas();

    }
}
