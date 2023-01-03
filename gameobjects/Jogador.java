package gameobjects;

public class Jogador {
    private String nome;
    private Baralho monte = new Baralho("monte do jogador");   
    //Gambiarra usar "monte do jogador" como tema padrão, o ideal seria adicionar o tema como parâmetro de Baralho.carregar()
    
    public Jogador(String nome) {
        this.nome = nome;
    }

    public void receberCarta(Carta carta) {
        this.monte.add(carta);
    }

    public Baralho getMonte() {
        return this.monte;
    }
}
