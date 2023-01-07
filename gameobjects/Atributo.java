package gameobjects;

import org.json.*;
import java.math.*;

public class Atributo {
    private String nome;
    private double valor;
    private String unidade;

    public Atributo(JSONObject data) {
        try {
            this.nome = data.getString("nome");
            this.valor = data.getDouble("valor");
            this.unidade = data.getString("unidade");
        } catch (Exception e) {
            System.out.println("Não foi possível gerar um atributo " + e);
        }
    }

    public String toString() {
        String output = nome + ": " + valor;
        if (!unidade.equals("null")) output += unidade;
        return output;
    }

    //public String toString();
}
