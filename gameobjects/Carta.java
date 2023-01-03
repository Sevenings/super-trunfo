package gameobjects;

import org.json.*;

public class Carta {
    private String nome;
    private String código;
    private String[] lista_atributos;
    private int[] atributos;

    public Carta(String nome, String código, String[] lista_atributos, int[] atributos) {
        this.nome = nome;
        this.código = código;
        this.lista_atributos = lista_atributos;
        this.atributos = atributos;
    }
    
    @Override
    public String toString() {
        String output = código + "|" + nome + "\t";   //Não colocar nomes grandes para a formatação ficar bonita
        if (nome.length() < 5)
            output += "\t";
        for (int i = 0; i < lista_atributos.length; i++) {
            output += " | " + lista_atributos[i] + ":";

            for (int j = 3 - (new Integer(atributos[i])).toString().length(); j > 0; j -= 1) {
                output += " ";
            }

            output += atributos[i];
        }
        return output; 
    }
}
