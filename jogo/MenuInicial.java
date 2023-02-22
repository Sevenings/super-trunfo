/* 
 * 
 * Recursos necessários:
 * Opção Jogar - escolhe seu nome e inicia o jogo
 * Escolher a temática do Baralho
 * Mudar opções gerais - velocidade do jogo
 */

package jogo;

import menu.chatmenu.*;
import menu.Command;
import org.json.*;
import gameobjects.Baralho;
import java.util.*;
import prettyprint.PrettyPrint;

public class MenuInicial {
    private Jogo jogo;
    private boolean on = true;
    
    public MenuInicial(Jogo jogo) {
        this.jogo = jogo;
    }

    public void open() {
        while (on) {
            PrettyPrint.limparTela();
            mostrarTitulo();
            Command selecionado = abrirMenuDeOpções();
            selecionado.action(new Object[0]);
        }
    }

    protected void mostrarTitulo() {
        String titulo = "  ████████\n ███░░░░░███\n░███    ░░░  █████ ████ ████████   ██████  ████████\n░░█████████ ░░███ ░███ ░░███░░███ ███░░███░░███░░███\n ░░░░░░░░███ ░███ ░███  ░███ ░███░███████  ░███ ░░░\n ███    ░███ ░███ ░███  ░███ ░███░███░░░   ░███\n░░█████████  ░░████████ ░███████ ░░██████  █████\n ░░░░░░░░░    ░░░░░░░░  ░███░░░   ░░░░░░  ░░░░░\n                        ░███\n                        █████\n                       ░░░░░\n                ███████████                                    ██████\n               ░█░░░███░░░█                                   ███░░███\n               ░   ░███  ░  ████████  █████ ████ ████████    ░███ ░░░   ██████\n                   ░███    ░░███░░███░░███ ░███ ░░███░░███  ███████    ███░░███\n                   ░███     ░███ ░░░  ░███ ░███  ░███ ░███ ░░░███░    ░███ ░███\n                   ░███     ░███      ░███ ░███  ░███ ░███   ░███     ░███ ░███\n                   █████    █████     ░░████████ ████ █████  █████    ░░██████\n                  ░░░░░    ░░░░░       ░░░░░░░░ ░░░░ ░░░░░  ░░░░░      ░░░░░░\n";
        PrettyPrint.efeitoMaquinaDeEscrever(titulo, 1);
    }

    protected Command abrirMenuDeOpções() {
        ChatMenu menu = new ChatMenu("Super Trunfo:");
        menu.add(new ChatMenuOption("Jogar", new ComandoJogar()));
        menu.add(new TematicasMenu("Tematicas"));
        menu.add(new ChatMenuOption("Sair", new ComandoSair()));

        return (Command) menu.open();
    }

    class ComandoJogar extends Command {
        public void action(Object[] args) {
            jogo.play();
        }
    }

    class TematicasMenu extends ChatMenu {
        public TematicasMenu(String nome) {
            super(nome);
            String filePath = Baralho.getCartasFile();
            JSONObject dadosListaCartas = Baralho.readFromFile(filePath);
            Iterator<String> nomesTemas = dadosListaCartas.keys();
            while (nomesTemas.hasNext()) {
                String tema = nomesTemas.next();
                this.add(new ChatMenuOption(tema, new ComandoSetTema(tema)));
            }
        }
    }   

    class ComandoSetTema extends Command {
        String tema;
        
        public ComandoSetTema(String tema) {
            this.tema = tema;
        }

        public void action(Object[] args) {
            jogo.setTema(tema);
        }
    }

    class ComandoSair extends Command {
        public void action(Object[] args) {
            on = false;
        }
    }
}
