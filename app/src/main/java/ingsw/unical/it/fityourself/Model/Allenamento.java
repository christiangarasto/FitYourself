package ingsw.unical.it.fityourself.Model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by chris on 11/01/2018.
 */

public class Allenamento {

    private String nomeAllenamento;
    private LinkedList<Esercizio> esercizi;


    public Allenamento(){}

    public Allenamento(String nomeAllenamento, LinkedList<Esercizio> esercizi){
        this.nomeAllenamento = nomeAllenamento;
        this.esercizi = esercizi;
    }

    public String getNomeAllenamento() {
        return nomeAllenamento;
    }

    public void setNomeAllenamento(String nomeAllenamento) {
        this.nomeAllenamento = nomeAllenamento;
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
