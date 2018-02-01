package ingsw.unical.it.fityourself.DOMAIN;

public class Obiettivo {

    private String tipo;
    private boolean valore;

    public Obiettivo(){}

    public Obiettivo(String tipo, boolean valore){
        this.tipo = tipo;
        this.valore = valore;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isValore() {
        return valore;
    }

    public void setValore(boolean valore) {
        this.valore = valore;
    }

    public String toString(){
        String ob = tipo + " ( ";
            if(valore)
                ob += "Raggiunto )\n";
            else
                ob += "Non raggiunto )\n";

        return ob;
    }
}
