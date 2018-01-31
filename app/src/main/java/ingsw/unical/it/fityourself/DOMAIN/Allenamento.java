package ingsw.unical.it.fityourself.DOMAIN;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class Allenamento {

    private String nomeAllenamento;
    private Date data;
    private String durata;
    private boolean completato;
    private LinkedList<Obiettivo> obiettivi;
    private LinkedList<Esercizio> esercizi;


    public Allenamento(){
    }

    public Allenamento(String nomeAllenamento, LinkedList<Esercizio> esercizi){
        this.nomeAllenamento = nomeAllenamento;
        this.esercizi = esercizi;
    }

    public void setObiettivi(ArrayList<Obiettivo> obiettivi) {
        LinkedList<Obiettivo> ob = new LinkedList<Obiettivo>();
        for(int i = 0; i < obiettivi.size(); i++){
            ob.add(obiettivi.get(i));
        }
        this.obiettivi = ob;
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

        for(int i = 0; i < esercizi.size(); i++){
            eserciziLL.add(esercizi.get(i));
        }

        this.esercizi = eserciziLL;
    }

    @Override
    public String toString() {
        String allenamento;

        allenamento = "Nome allenamento: " + nomeAllenamento + ".\nEsercizi:";

        if (esercizi != null) {
            for (int i = 0; i < esercizi.size(); i++){
                allenamento += "\n- ";
                allenamento += (esercizi.get(i).toString() + ".");
            }
        }

        return allenamento;
    }

    public void setLinkedEsercizi(LinkedList<Esercizio> esercizi) {
        this.esercizi = esercizi;
    }
}
