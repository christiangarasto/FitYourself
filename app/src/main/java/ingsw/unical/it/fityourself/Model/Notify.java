package ingsw.unical.it.fityourself.Model;

/**
 * Created by valentina on 11/01/2018.
 */

public class Notify {

    private boolean abilita;
    private boolean intermedio;
    private boolean finale;
    private boolean anomalie;
    private String unitaDiMisura;
    private int valoreIntermedio;

    /*public Notify (){

        this.abilita = true;
        this.intermedio = false;
        this.finale = false;
        this.anomalie = false;
        this.unitaDiMisura = "0";
        this.valoreIntermedio = 0;
    }*/

    public Notify(boolean abilita, boolean intermedio, boolean finale, boolean anomalie, String unitaDiMisura, int valoreIntermedio){

        this.abilita = abilita;
        this.intermedio = intermedio;
        this.finale = finale;
        this.anomalie = anomalie;
        this.unitaDiMisura = unitaDiMisura;
        this.valoreIntermedio = valoreIntermedio;
    }

    public boolean isAbilita() {
        return abilita;
    }

    public void setAbilita(boolean abilita) {
        this.abilita = abilita;
    }

    public boolean isIntermedio() {
        return intermedio;
    }

    public void setIntermedio(boolean intermedio) {
        this.intermedio = intermedio;
    }

    public boolean isFinale() {
        return finale;
    }

    public void setFinale(boolean finale) {
        this.finale = finale;
    }

    public boolean isAnomalie() {
        return anomalie;
    }

    public void setAnomalie(boolean anomalie) {
        this.anomalie = anomalie;
    }

    public String getUnitaDiMisura() {
        return unitaDiMisura;
    }

    public void setUnitaDiMisura(String unitaDiMisura) {
        this.unitaDiMisura = unitaDiMisura;
    }

    public int getValoreIntermedio() {
        return valoreIntermedio;
    }

    public void setValoreIntermedio(int valoreIntermedio) {
        this.valoreIntermedio = valoreIntermedio;
    }
}
