package gameobjects;

import org.json.*;
import java.math.*;

public class Atributo implements Comparable<Atributo> {
    private String nome;
    private double valor;
    private String unidade;
    private boolean maiorMelhor = true;

    public Atributo(JSONObject data) {
        try {
            this.nome = data.getString("nome");
            this.valor = data.getDouble("valor");
            this.unidade = data.getString("unidade");
        } catch (Exception e) {
            System.out.println("Não foi possível gerar um atributo " + e);
        }
        try {
            this.maiorMelhor = data.getBoolean("maiorMelhor");
        } catch (Exception e) {}
    }

    public String toString() {
        String output = nome + ": " + valor;
        if (!unidade.equals("null")) output += unidade;
        return output;
    }

    public int compareTo(Atributo atributo) {
        int value = (int) (this.getValor() - atributo.getValor());
        if (!maiorMelhor)
            value *= -1;
        return value;
    }

    public double getValor() {
        return this.valor;
    }

    public String getNome() {
        return this.nome;
    }

    //public String toString();
}
