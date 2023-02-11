package menu;

public abstract class Option {
    protected Object content;
    protected String nome;
    protected Menu upper;

    public abstract Object select(Menu selector_menu); 

    public String getNome() {
        return this.nome;
    }
 
/*
    Talvez não faça sentido deixar como public
    Se não faz sentido deixar como public, talvez nem faça sentido esse método existir
    public Object getContent() {
        return this.content;
    }
*/

    public void setUpper(Menu upper) {
        this.upper = upper;
    }
}
