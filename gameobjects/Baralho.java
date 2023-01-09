package gameobjects;

import java.util.*;
import org.json.*;
import java.io.*;
import java.util.stream.*;
import jogador.*;

public class Baralho extends LinkedList<Carta> {
    private String tema;
    private String temas_filepath = "lista_cartas.json";

    public void carregar(String tema) throws ThemeNotFoundException {
        this.tema = tema;
        try {
            JSONObject data_tema = readFromFile(temas_filepath).getJSONObject(tema);
            JSONArray data_cartas = data_tema.getJSONArray("cartas");
            for (int i = 0; i < data_cartas.length(); i++) {
                JSONObject carta_json = data_cartas.getJSONObject(i);
                this.add(new Carta(carta_json));
            } 
        } catch (JSONException e) {
            throw new ThemeNotFoundException("Falha ao buscar o tema " + tema + " no arquivo " + temas_filepath);
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
