package gameobjects;

import org.json.*;
import java.util.*;

public class Carta {
    private String nome;
    private String id;
    private ArrayList<Atributo> atributos;
    private ArrayList<String> curiosidades;

    public Carta(String nome, String id, ArrayList<Atributo> atributos, ArrayList<String> curiosidades) {
        this.nome = nome;
        this.id = id;
        this.atributos = atributos;
        this.curiosidades = curiosidades;
    }
    
    @Override
    public String toString() {
        String output = id + "|" + nome + "\t";   //Não colocar nomes grandes para a formatação ficar bonita
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
