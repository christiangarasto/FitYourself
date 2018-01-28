package ingsw.unical.it.fityourself.Model;

/**
 * Created by chris on 11/01/2018.
 */


public class Esercizio {

    private String nomeEsercizio;
    private String durata;
    private int numeroDurata;
    private String nomeDurata;

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

    public int getNumeroDurata() {

        String tmp = "";

        /*for(int chr = 0; chr < durata.length(); chr++)
        {
            if(durata.charAt(chr) > 0 && durata.charAt(chr) < 9)
                tmp += durata.charAt(chr);
        }*/

        tmp += Character.toString(durata.charAt(0));
        tmp +=Character.toString(durata.charAt(1));

        numeroDurata = Integer.parseInt(tmp);

        return numeroDurata;
    }

    public void setNumeroDurata(int numeroDurata) {
        this.numeroDurata = numeroDurata;
    }

    public String getNomeDurata() {

        if(durata.charAt(2) == 's' || durata.charAt(3) == 's')
            nomeDurata = "serie";
        else if(durata.charAt(2) == 'm' || durata.charAt(3) == 'm' || durata.charAt(4) == 'm')
            nomeDurata = "minuti";

        return nomeDurata;
    }

    public void setNomeDurata(String nomeDurata) {
        this.nomeDurata = nomeDurata;
    }

    @Override
    public String toString() {
        return nomeEsercizio + ": " + durata;
    }
}
