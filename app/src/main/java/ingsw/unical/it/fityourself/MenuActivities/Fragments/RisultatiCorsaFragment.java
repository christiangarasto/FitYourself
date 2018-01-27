package ingsw.unical.it.fityourself.MenuActivities.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Chronometer;
import android.widget.Toast;

import ingsw.unical.it.fityourself.R;

/**
 * Created by valentina on 26/01/2018.
 */

public class RisultatiCorsaFragment extends Fragment implements GenericFragment{

    private View rootView;
    private CheckedTextView nPassi;
    private CheckedTextView distanza;
    private CheckedTextView nCalorieBruciate;
    private Button escibtn, salvabtn;
    private Chronometer time;

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

        time = (Chronometer) rootView.findViewById(R.id.tempo);
        time.setFormat("%s");
        time.setBase(CorsaFragment.getTempo().getBase());

        escibtn = (Button) rootView.findViewById(R.id.esci);
        salvabtn = (Button) rootView.findViewById(R.id.salva);

        nPassi = (CheckedTextView) rootView.findViewById(R.id.passiEffettuati);
        distanza = (CheckedTextView) rootView.findViewById(R.id.distanzaPercorsa);
        nCalorieBruciate = (CheckedTextView) rootView.findViewById(R.id.calorieBruciate);

        nPassi.setText(CorsaFragment.getPassiEffettuati().getText().toString());
        distanza.setText(CorsaFragment.getDistanzaPercorsa().getText().toString());
        nCalorieBruciate.setText(CorsaFragment.getCalorieBruciate().getText().toString());

        int passi = Integer.parseInt(nPassi.getText().toString());
        int obPassi, obDist, obKcal;

        if(!CorsaFragment.getObiettivoP().getText().toString().equals(""))
               obPassi = Integer.parseInt(CorsaFragment.getObiettivoP().getText().toString());
        else
            obPassi = 0;


        int dist = Integer.parseInt(distanza.getText().toString());
        if(!CorsaFragment.getObiettivoD().getText().toString().equals(""))
            obDist = Integer.parseInt(CorsaFragment.getObiettivoD().getText().toString());
        else
            obDist = 0;

        int kCal = Integer.parseInt(nCalorieBruciate.getText().toString());
        if(!CorsaFragment.getObiettivoC().getText().toString().equals(""))
            obKcal = Integer.parseInt(CorsaFragment.getObiettivoC().getText().toString());
        else
            obKcal = 0;


        if(CorsaFragment.getPassiEffettuati().length() > 0)
        {
            if(passi >= obPassi && obPassi > 0)
            {
                nPassi.setCheckMarkDrawable(R.drawable.checked);
                nPassi.setTextColor(Color.GREEN);
            }

            else
            {
                nPassi.setCheckMarkDrawable(R.drawable.notchecked);
                nPassi.setTextColor(Color.RED);
            }
        }

        if(CorsaFragment.getDistanzaPercorsa().length() > 0)
        {
            if(dist >= obDist && obDist > 0)
            {
                distanza.setCheckMarkDrawable(R.drawable.checked);
                distanza.setTextColor(Color.GREEN);
            }
            else
            {
                distanza.setCheckMarkDrawable(R.drawable.notchecked);
                distanza.setTextColor(Color.RED);
            }
        }

        if(CorsaFragment.getCalorieBruciate().length() > 0)
        {
            if( kCal >= obKcal && obKcal > 0)
            {
                nCalorieBruciate.setCheckMarkDrawable(R.drawable.checked);
                nCalorieBruciate.setTextColor(Color.GREEN);
            }
            else
            {
                nCalorieBruciate.setCheckMarkDrawable(R.drawable.notchecked);
                nCalorieBruciate.setTextColor(Color.RED);
            }
        }

        escibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GenericFragment dati = AllenamentoFragment.getInstance();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, dati.getFragment());
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }



}
