package gameobjects;

import org.json.*;
import java.util.*;

public class Carta {
    private String nome;
    private String id;
    private ArrayList<Atributo> atributos;
    private ArrayList<String> curiosidades;
    private boolean isTrunfo = false;

    public Carta(JSONObject data) {
        try { 
            this.nome = data.getString("nome");
            this.id = data.getString("id");

            this.isTrunfo = data.getBoolean("trunfo");

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

    public String getNome() {
        return this.nome;
    }

    public String getTitulo() {
        String titulo = getId() + " | " + getNome();
        if (isTrunfo()) 
            titulo += " SUPER TRUNFO";
        return titulo;
    }

    public String getId() {
        return this.id;
    }

    public boolean isTrunfo() {
        return this.isTrunfo;
    }

    public List<Atributo> getListaAtributos() {
        return atributos;
    }

    public Atributo getAtributo(int n) {
        return atributos.get(n);
    }

    public String getRandomCuriosidade() {
        Random roleta = new Random();
        int size_curiosidades = curiosidades.size();
        try {
            return curiosidades.get(roleta.nextInt(size_curiosidades));
        } catch (NullPointerException npe) {
            return null;
        }
    }
}
