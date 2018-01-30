package ingsw.unical.it.fityourself.DOMAIN;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class Allenamento {

    private LinkedList<Esercizio> esercizi;

    private String nomeAllenamento;
    private Date data;
    private String durata;
    private boolean completato;
    private LinkedList<Obiettivo> obiettivi;


    public Allenamento(){
    }

    public Allenamento(String nomeAllenamento, LinkedList<Esercizio> esercizi){
        this.nomeAllenamento = nomeAllenamento;
        this.esercizi = esercizi;
    }

    public void setObiettivi(LinkedList<Obiettivo> obiettivi) {
        this.obiettivi = obiettivi;
    }

    public LinkedList<Obiettivo> getObiettivi() {
        return obiettivi;
    }

    public String getNomeAllenamento() {
        return nomeAllenamento;
    }

    public void setNomeAllenamento(String nomeAllenamento) {
        this.nomeAllenamento = nomeAllenamento;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isCompletato() {
        return completato;
    }

    public void setCompletato(boolean completato) {
        this.completato = completato;
    }

    public LinkedList<Esercizio> getEsercizi() {
        return esercizi;
    }

    public void setEsercizi(ArrayList<Esercizio> esercizi) {
        LinkedList<Esercizio> eserciziLL = new LinkedList<Esercizio>();

        for(Esercizio e : esercizi){
            eserciziLL.add(e);
        }

        this.esercizi = eserciziLL;
    }

    @Override
    public String toString() {
        String allenamento;

                allenamento = "Nome allenamento: " + nomeAllenamento + ".\nEsercizi:";

                for(Esercizio e : esercizi){
                    allenamento += "\n- ";
                    allenamento += (e.toString() + ".");
                }


        return allenamento;
    }
}
