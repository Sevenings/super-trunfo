package gameobjects;

import org.json.*;
import java.util.*;

public class Carta {
    private String nome;
    private String id;
    private ArrayList<Atributo> atributos;
    private ArrayList<String> curiosidades;

    public Carta(JSONObject data) {
        try { 
            this.nome = data.getString("nome");
            this.id = data.getString("id");

            this.atributos = new ArrayList<Atributo>(4);
            this.atributos.add(new Atributo(data.getJSONObject("atr1"))); 
            this.atributos.add(new Atributo(data.getJSONObject("atr2"))); 
            this.atributos.add(new Atributo(data.getJSONObject("atr3"))); 
            this.atributos.add(new Atributo(data.getJSONObject("atr4"))); 

            this.curiosidades = new ArrayList<String>();
            JSONArray data_curiosidades = data.getJSONArray("curiosidades");
            for (int i = 0; i < data_curiosidades.length(); i++) {
                this.curiosidades.add(data_curiosidades.getString(i));
            } 
        } catch (Exception e) {
            System.out.println("Não foi possível carregar carta " + e);
        }
    }
    
    @Override
    public String toString() {
        String output = id + " " + nome;
        for (String curiosidade : curiosidades) {   // No Futuro, mostrar 1 curiosidade aleatória
            output += "\n " + "\"" + curiosidade + "\"";
        }
        for (Atributo atr : atributos) {
            output += "\n *" + atr.toString();
        }
        
        return output;
    }
}
