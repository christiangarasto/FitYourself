package ingsw.unical.it.fityourself.MenuActivities.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import ingsw.unical.it.fityourself.R;

/**
 * Created by valentina on 26/01/2018.
 */

public class RisultatiCorsaFragment extends Fragment implements GenericFragment{

    private View rootView;
    private CheckedTextView nPassi;
    private CheckedTextView distanza;
    private CheckedTextView nCalorieBruciate;

    private static RisultatiCorsaFragment risultatiCorsaFragment = null;

    private RisultatiCorsaFragment(){

    }

    public static RisultatiCorsaFragment getInstance(){
        if(risultatiCorsaFragment == null)
            risultatiCorsaFragment = new RisultatiCorsaFragment();

        return risultatiCorsaFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_risultati_corsa, container, false);

        nPassi = (CheckedTextView) rootView.findViewById(R.id.passiEffettuati);
        distanza = (CheckedTextView) rootView.findViewById(R.id.distanzaPercorsa);
        nCalorieBruciate = (CheckedTextView) rootView.findViewById(R.id.calorieBruciate);

        nPassi.setText(CorsaFragment.getPassiEffettuati().toString());
        distanza.setText(CorsaFragment.getDistanzaPercorsa().toString());
        nCalorieBruciate.setText(CorsaFragment.getCalorieBruciate().toString());

        int passi = Integer.parseInt(nPassi.toString());
        int obPassi = Integer.parseInt(CorsaFragment.getObiettivoP().toString());

        int dist = Integer.parseInt(distanza.toString());
        int obDist = Integer.parseInt(CorsaFragment.getObiettivoD().toString());

        int kCal = Integer.parseInt(nCalorieBruciate.toString());
        int obKcal = Integer.parseInt(CorsaFragment.getObiettivoC().toString());

        nPassi.setChecked(true);
        distanza.setChecked(false);

        if(CorsaFragment.getPassiEffettuati().length() > 0)
        {
            if(passi >= obPassi && obPassi > 0)
                nPassi.setChecked(true);

            else
                nPassi.setChecked(false);
        }

        if(CorsaFragment.getDistanzaPercorsa().length() > 0)
        {
            if(dist >= obDist && obDist > 0)
                distanza.setChecked(true);
            else
                distanza.setChecked(false);
        }

        if(CorsaFragment.getCalorieBruciate().length() > 0)
        {
            if( kCal >= obKcal && obKcal > 0)
                nCalorieBruciate.setChecked(true);
            else
                nCalorieBruciate.setChecked(false);
        }

        return rootView;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }



}
