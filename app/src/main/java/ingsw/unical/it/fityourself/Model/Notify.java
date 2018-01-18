package ingsw.unical.it.fityourself.Model;

import android.util.Log;

/**
 * Created by andry on 18/01/18.
 */

public class Notify {

    private boolean abilita;
    private boolean intermedio;
    private boolean finale;
    private boolean anomalie;
    private String unitaDiMisura;
    private int valoreIntermedio;

    public Notify(){

        Log.e("costruttoreSP::","COSTRUTTORE SENZA PARAMETRI CHIAMATO_____________");
        this.abilita = false;
        this.intermedio = false;
        this.finale = false;
        this.anomalie = false;
        this.unitaDiMisura = "niente";
        this.valoreIntermedio = 0;


        Log.e("ABILITA::",Boolean.toString(abilita));
        Log.e("INTERMEDIO::",Boolean.toString(intermedio));
        Log.e("FINALE::",Boolean.toString(finale));
        Log.e("ANOMALIE::",Boolean.toString(anomalie));
        Log.e("UNITADIMISURA::",unitaDiMisura);
        Log.e("VALOREINTERMEDIO::",Integer.toString(valoreIntermedio));
        Log.e("costruttoreSP:::","CREATO CORRETTAMENTE________________________________");
    }


    public Notify(boolean abilita, boolean intermedio, boolean finale, boolean anomalie, String unitaDiMisura, int valoreIntermedio){

        this.abilita = abilita;
        this.intermedio = intermedio;
        this.finale = finale;
        this.anomalie = anomalie;
        this.unitaDiMisura = unitaDiMisura;
        this.valoreIntermedio = valoreIntermedio;

        Log.e("costruttoreConParametri","usato correttamente____________________________");
    }

    public boolean isAbilita() {
        return abilita;
    }

    public void setAbilita(boolean abilita) {
        this.abilita = abilita;
        String f = Boolean.toString(abilita);
        Log.e("ABILITA:::",f);

    }

    public boolean isIntermedio() {
        return intermedio;
    }

    public void setIntermedio(boolean intermedio) {
        this.intermedio = intermedio;
        String f = Boolean.toString(intermedio);
        Log.e("INTERMEDIO:::",f);
    }

    public boolean isFinale() {
        return finale;
    }

    public void setFinale(boolean finale) {
        this.finale = finale;
        String f = Boolean.toString(finale);
        Log.e("FINALE:::",f);
    }

    public boolean isAnomalie() {
        return anomalie;
    }

    public void setAnomalie(boolean anomalie) {
        this.anomalie = anomalie;
        String f = Boolean.toString(anomalie);
        Log.e("ANOMALIE:::",f);
    }

    public String getUnitaDiMisura() {
        return unitaDiMisura;
    }

    public void setUnitaDiMisura(String unitaDiMisura) {
        this.unitaDiMisura = unitaDiMisura;
        Log.e("UNITADIMISURA::",unitaDiMisura);
    }

    public int getValoreIntermedio() {
        return valoreIntermedio;
    }

    public void setValoreIntermedio(int valoreIntermedio) {
        this.valoreIntermedio = valoreIntermedio;
        Log.e("VALOREINTERMEDIO::",Integer.toString(valoreIntermedio));
    }
}
