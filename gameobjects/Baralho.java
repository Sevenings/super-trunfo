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
            JSONObject dados_do_tema = readFromFile(temas_filepath).getJSONObject(tema);
            JSONArray dados_lista_de_cartas = dados_do_tema.getJSONArray("cartas");

            for (int i = 0; i < dados_lista_de_cartas.length(); i++) {
                JSONObject carta_json = dados_lista_de_cartas.getJSONObject(i);
                for (int n_atributo = 1; n_atributo <= 4; n_atributo++) {
                    String key_do_atributo = "atr" + n_atributo;
                    JSONObject dados_atributo_n = dados_do_tema.getJSONObject(key_do_atributo);

                    String nome_do_atributo_n = dados_atributo_n.getString("nome");
                    carta_json.getJSONObject(key_do_atributo).put("nome", nome_do_atributo_n);

                    String unidade_do_atributo_n = dados_atributo_n.getString("unidade");
                    carta_json.getJSONObject(key_do_atributo).put("unidade", unidade_do_atributo_n);
                }
                
                this.add(new Carta(carta_json));
            } 

        } catch (JSONException e) {
            e.printStackTrace();
            throw new ThemeNotFoundException("Falha ao buscar o tema " + tema + " no arquivo " + temas_filepath);
        }
    }

    public void embaralhar() {
        Collections.shuffle(this);
    }

    public void distribuir(List<Jogador> jogadores) {
        Iterator<Carta> iterator = this.iterator();
        int n_jogadores = jogadores.size();

        int i = 0;
        while (iterator.hasNext()) {
            jogadores.get(i % n_jogadores).receberCarta(this.pegarDoTopo());
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
