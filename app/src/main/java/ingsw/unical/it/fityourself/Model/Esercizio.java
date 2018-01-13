package ingsw.unical.it.fityourself.Model;

/**
 * Created by chris on 11/01/2018.
 */


public class Esercizio {

    private String nomeEsercizio;
    private String durata;

    public Esercizio(){};

    public Esercizio(String nomeEsercizio, String durata){
        this.nomeEsercizio = nomeEsercizio;
        this.durata = durata;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public String getNomeEsercizio() {
        return nomeEsercizio;
    }

    public void setNomeEsercizio(String nomeEsercizio) {
        this.nomeEsercizio = nomeEsercizio;
    }

    @Override
    public String toString() {
        return nomeEsercizio + ": " + durata;
    }
}
