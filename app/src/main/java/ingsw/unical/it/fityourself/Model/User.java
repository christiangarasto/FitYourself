package ingsw.unical.it.fityourself.Model;

/**
 * Created by valentina on 03/01/2018.
 */

public class User {

    private String nome;
    private String cognome;
    private double peso;
    private double altezza;
    private int eta;
    private String sesso;
    private boolean sport;

    public User(){

        this.nome = "";
        this.cognome = "";
        this.peso = 0;
        this.altezza = 0;
        this.eta = 0;
        this.sesso = "";
        this.sport = false;

    }


    public User(String nome, String cognome, double peso, double altezza, int eta, String sesso, boolean sport){

            this.nome = nome;
            this.cognome = cognome;
            this.peso = peso;
            this.altezza = altezza;
            this.eta = eta;
            this.sesso = sesso;
            this.sport = sport;

    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public double getPeso() {
        return peso;
    }

    public double getAltezza() {
        return altezza;
    }

    public int getEta() {
        return eta;
    }

    public String getSesso() {
        return sesso;
    }

    public boolean isSport() {
        return sport;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setAltezza(double altezza) {
        this.altezza = altezza;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public void setSport(boolean sport) {
        this.sport = sport;
    }

}
