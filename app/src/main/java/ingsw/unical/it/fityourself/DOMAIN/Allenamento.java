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
    private LinkedList<Esercizio> esercizi;

    private String passiEffettuati;
    private String distanzaPercorsa;
    private String calorieBruciate;

    private Obiettivo obiettivoPassi;
    private Obiettivo obiettivoDistanza;
    private Obiettivo obiettivoCalorie;


    public Allenamento(){
    }

    public Allenamento(String nomeAllenamento, LinkedList<Esercizio> esercizi){
        this.nomeAllenamento = nomeAllenamento;
        this.esercizi = esercizi;
    }

    public void setObiettivoCalorie(Obiettivo obiettivoCalorie) {
        this.obiettivoCalorie = obiettivoCalorie;
    }

    public Obiettivo getObiettivoCalorie() {
        return obiettivoCalorie;
    }

    public void setObiettivoDistanza(Obiettivo obiettivoDistanza) {
        this.obiettivoDistanza = obiettivoDistanza;
    }

    public Obiettivo getObiettivoDistanza() {
        return obiettivoDistanza;
    }

    public void setObiettivoPassi(Obiettivo obiettivoPassi) {
        this.obiettivoPassi = obiettivoPassi;
    }

    public Obiettivo getObiettivoPassi() {
        return obiettivoPassi;
    }

    public void setPassiEffettuati(String passiEffettuati) {
        this.passiEffettuati = passiEffettuati;
    }

    public String getPassiEffettuati() {
        return passiEffettuati;
    }

    public String getCalorieBruciate() {
        return calorieBruciate;
    }

    public void setCalorieBruciate(String calorieBruciate) {
        this.calorieBruciate = calorieBruciate;
    }

    public String getDistanzaPercorsa() {
        return distanzaPercorsa;
    }

    public void setDistanzaPercorsa(String distanzaPercorsa) {
        this.distanzaPercorsa = distanzaPercorsa;
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

        allenamento = "Nome allenamento: " + nomeAllenamento + ".\n";

        if (esercizi != null) {
            allenamento += "Esercizi: ";
            for (int i = 0; i < esercizi.size(); i++){
                allenamento += "\n- ";
                allenamento += (esercizi.get(i).toString() + ".");
            }
        }
        /*
        else if(obiettivi != null){

            allenamento += "Durata: " + durata + "\n";

            if(obiettivi.size() > 0) {
                allenamento += "Obiettivi:";
                for (int i = 0; i < obiettivi.size(); i++) {
                    allenamento += "\n- " + obiettivi.get(i).toString() + ": ";
                        if(obiettivi.get(i).isValore())
                            allenamento += "Raggiunto.\n";
                        else
                            allenamento += "Non raggiunto.\n";
                }
            }
        }
        */
        return allenamento;
    }

    public void setLinkedEsercizi(LinkedList<Esercizio> esercizi) {
        this.esercizi = esercizi;
    }
}
