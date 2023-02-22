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
import jogador.*;
import gameobjects.ThemeNotFoundException;

public class MenuInicial {
    private Jogo jogo;
    private boolean on = true;
    private static boolean primeiraVezAberto = true;
    
    public MenuInicial(Jogo jogo) {
        this.jogo = jogo;
    }

    public void open() {
        while (on) {
            limparTela();
            mostrarTematicaSelecionada();
            Command selecionado = abrirMenuDeOpções();
            selecionado.action(new Object[0]);
        }
    }

    protected void mostrarTematicaSelecionada() {
        String nome_tema = jogo.getTema();
        String texto = "Nenhum Tema selecionado!";
        if (nome_tema != null)
            texto = "Temática: " + jogo.getTema();
        System.out.println(texto);
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
            String nomePlayer = perguntarNome();
            jogo.addJogador(new JogadorHumano(nomePlayer));
            jogo.addJogador(new Bot("Senku-3000"));
            
            boolean foundError = false;
            do {
                try {
                    jogo.play();
                } catch (ThemeNotFoundException tnfe) {
                    foundError = true;
                    PrettyPrint.timedPrint("Aviso! Tema não selecionado!", 500);
                    ChatMenu menuTemas = new TematicasMenu("Por favor selecione um tema:");
                    Command temaSelecionado = (Command) menuTemas.open();
                    temaSelecionado.action(new Object[0]);
                }
            } while (foundError == true);
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

    private String perguntarNome() {
        limparTela();
        System.out.println("Nome do Jogador:");
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public static void limparTela() {
        PrettyPrint.limparTela();
        mostrarTitulo();
    }
    
    protected static void mostrarTitulo() {
        String titulo = "  ████████\n ███░░░░░███\n░███    ░░░  █████ ████ ████████   ██████  ████████\n░░█████████ ░░███ ░███ ░░███░░███ ███░░███░░███░░███\n ░░░░░░░░███ ░███ ░███  ░███ ░███░███████  ░███ ░░░\n ███    ░███ ░███ ░███  ░███ ░███░███░░░   ░███\n░░█████████  ░░████████ ░███████ ░░██████  █████\n ░░░░░░░░░    ░░░░░░░░  ░███░░░   ░░░░░░  ░░░░░\n                        ░███\n                        █████\n                       ░░░░░\n                ███████████                                    ██████\n               ░█░░░███░░░█                                   ███░░███\n               ░   ░███  ░  ████████  █████ ████ ████████    ░███ ░░░   ██████\n                   ░███    ░░███░░███░░███ ░███ ░░███░░███  ███████    ███░░███\n                   ░███     ░███ ░░░  ░███ ░███  ░███ ░███ ░░░███░    ░███ ░███\n                   ░███     ░███      ░███ ░███  ░███ ░███   ░███     ░███ ░███\n                   █████    █████     ░░████████ ████ █████  █████    ░░██████\n                  ░░░░░    ░░░░░       ░░░░░░░░ ░░░░ ░░░░░  ░░░░░      ░░░░░░\n";
        if (primeiraVezAberto) {
            PrettyPrint.efeitoMaquinaDeEscrever(titulo, 1);
            primeiraVezAberto = false;
        } else {
            System.out.println(titulo);
        }
    }
}
