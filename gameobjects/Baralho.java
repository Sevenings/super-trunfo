package gameobjects;

import java.util.*;
import org.json.*;
import java.io.*;
import java.util.stream.*;

public class Baralho extends LinkedList<Carta> {
    private String tema;
    private String temas_filepath = "lista_teste.json";

    public void carregar(String tema) {
        this.tema = tema;
        JSONObject tema_json = readFromFile(temas_filepath).getJSONObject(tema);
        JSONArray atributos = tema_json.getJSONArray("atributos");
        JSONArray lista_cartas_json = tema_json.getJSONArray("cartas");

        String[] atributos_names = new String[atributos.length()];	//Lê os nomes dos atributos na JSONArray atributos
		for (int j = 0; j < atributos.length(); j++) {
			atributos_names[j] = atributos.get(j).toString();
		}
       
        for (int i = 0; i < lista_cartas_json.length(); i++) {		//Lê as informações de cada carta e salva no próprio array
            JSONObject carta_json = new JSONObject(lista_cartas_json.get(i).toString());
            String nome = carta_json.getString("name");				//Nome da carta
            String código = carta_json.getString("codigo");			//Código da carta
            
            int[] atributos_values = new int[atributos_names.length];	//Valores de cada atributo da carta
            for (int j = 0; j < atributos.length(); j++)
                atributos_values[j] = carta_json.getInt(atributos_names[j]);
            this.add(new Carta(nome, código, atributos_names, atributos_values));	//Gera a carta e adiciona na própria Array de cartas
        }
    }

    public void embaralhar() {
        Collections.shuffle(this);
    }

    public void distribuir(Jogador[] jogadores) {
        Iterator<Carta> iterator = this.iterator();
        int n_jogadores = jogadores.length;

        int i = 0;
        while (iterator.hasNext()) {
            jogadores[i % n_jogadores].receberCarta(this.pegarDoTopo());
            i++;
        }
    }

    public void listarCartas() {	
        for (Carta card : this) {
            System.out.println(card.toString());
        }
    }

    public Carta pegarDoTopo() {
        return this.pop();
    }    

    private JSONObject readFromFile(String pathName) {
		JSONObject json = null;
		
        try {
        	
        	//Lê o JSON e passa os dados para um buffer de leitura
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            
            //Passa o os dados do buffer para uma string
           
            String json_text = "";
            while (bufferedReader.ready()) { 
                json_text = json_text.concat(bufferedReader.readLine());
            }
            
            //Cria um objeto JSON Java
            json = new JSONObject(json_text);
      
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Não foi possível ler o arquivo json");
        }
        
        return json;
	}
}
