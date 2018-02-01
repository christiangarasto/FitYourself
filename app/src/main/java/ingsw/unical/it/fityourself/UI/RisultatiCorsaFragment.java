package ingsw.unical.it.fityourself.UI;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

import ingsw.unical.it.fityourself.DOMAIN.Allenamento;
import ingsw.unical.it.fityourself.DOMAIN.Obiettivo;
import ingsw.unical.it.fityourself.R;

public class RisultatiCorsaFragment extends Fragment implements GenericFragment{

    private View rootView;
    private CheckedTextView nPassi;
    private CheckedTextView distanza;
    private CheckedTextView nCalorieBruciate;
    private Button escibtn, salvabtn;
    private Chronometer time;

    private static FirebaseDatabase mFirebaseInstance;
    private static DatabaseReference mFirebaseDatabase;
    private String uid;

    public RisultatiCorsaFragment(){
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Allenamenti Effettuati");
        uid = FirebaseAuth.getInstance().getUid();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Allenamento concluso");

        rootView = inflater.inflate(R.layout.fragment_risultati_corsa, container, false);

        time = (Chronometer) rootView.findViewById(R.id.tempo);
        if(CorsaFragment.isCronometroAvviatoAlmenoUnaVolta()) {
            time.setFormat("Durata allenamento: %s");
            time.setBase(CorsaFragment.getTempo());
        }
        //else{
        //    time.setFormat("Durata allenamento: 00:00:00");
        //}
        escibtn = (Button) rootView.findViewById(R.id.esci);
        salvabtn = (Button) rootView.findViewById(R.id.salva);

        nPassi = (CheckedTextView) rootView.findViewById(R.id.passiEffettuati);
        distanza = (CheckedTextView) rootView.findViewById(R.id.distanzaPercorsa);
        nCalorieBruciate = (CheckedTextView) rootView.findViewById(R.id.calorieBruciate);

        nPassi.setText(CorsaFragment.getPassiEffettuati().getText().toString());
        distanza.setText(CorsaFragment.getDistanzaPercorsa().getText().toString());
        nCalorieBruciate.setText(CorsaFragment.getCalorieBruciate().getText().toString());

        final int passi = Integer.parseInt(nPassi.getText().toString());
        final int obPassi, obDist, obKcal;

        if(!CorsaFragment.getObiettivoP().getText().toString().equals(""))
               obPassi = Integer.parseInt(CorsaFragment.getObiettivoP().getText().toString());
        else
            obPassi = 0;


        final int dist = Integer.parseInt(distanza.getText().toString());
        if(!CorsaFragment.getObiettivoD().getText().toString().equals(""))
            obDist = Integer.parseInt(CorsaFragment.getObiettivoD().getText().toString());
        else
            obDist = 0;

        int kCal = (int) Double.parseDouble(nCalorieBruciate.getText().toString());
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

        salvabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Allenamento corsa = new Allenamento();
/*
                if(nCalorieBruciate.getCurrentTextColor() == Color.GREEN ||
                   nPassi.getCurrentTextColor() == Color.GREEN ||
                   distanza.getCurrentTextColor() == Color.GREEN )
                 */
                Double caloB = Double.parseDouble(CorsaFragment.getCalorieBruciate().getText().toString());
                int distP = Integer.parseInt(CorsaFragment.getDistanzaPercorsa().getText().toString());
                int passiE = Integer.parseInt(CorsaFragment.getPassiEffettuati().getText().toString());

                if(caloB == 0 && distP == 0 && passiE == 0 || CorsaFragment.getTempo() == 0){
                        Toast.makeText(getContext(), "L'allenamento non verrà memorizzato perchè non completato!", Toast.LENGTH_LONG).show();
                }else{
                    corsa.setCompletato(true);
                    corsa.setNomeAllenamento("Sessione di corsa");
                    corsa.setData(Calendar.getInstance().getTime());
                    corsa.setDurata(time.getContentDescription().toString());

                    Obiettivo passi = null;
                    Obiettivo calorie = null;
                    Obiettivo distanzaPercorsa = null;

                    if(nPassi.getCurrentTextColor() == Color.GREEN ) {
                        passi = new Obiettivo("Passi: " + nPassi.getText(), true);
                    }else if(Integer.parseInt(nPassi.getText().toString()) < obPassi){
                        passi = new Obiettivo("Passi: " + nPassi.getText(), false);
                    }

                    if(nCalorieBruciate.getCurrentTextColor() == Color.GREEN ) {
                        calorie = new Obiettivo("Calorie: " + nCalorieBruciate.getText(), true);
                    }else if(Double.parseDouble(nCalorieBruciate.getText().toString()) < obKcal){
                        calorie = new Obiettivo("Calorie: " + nCalorieBruciate.getText(), false);
                    }

                    if(distanza.getCurrentTextColor() == Color.GREEN ) {
                        distanzaPercorsa = new Obiettivo("Distanza: " + distanza.getText(), true);
                    }else if(Integer.parseInt(distanza.getText().toString()) < obDist){
                        distanzaPercorsa = new Obiettivo("Distanza: " + distanza.getText(), false);
                    }

                            corsa.setObiettivoPassi(passi);
                                corsa.setPassiEffettuati(nPassi.getText().toString());
                            corsa.setObiettivoCalorie(calorie);
                                corsa.setCalorieBruciate(nCalorieBruciate.getText().toString());
                            corsa.setObiettivoDistanza(distanzaPercorsa);
                                corsa.setDistanzaPercorsa(distanza.getText().toString());

                    mFirebaseDatabase.child(uid).child(corsa.getData().toString()).setValue(corsa);

                    Toast.makeText(getContext(), "Allenamento salvato con successo!", Toast.LENGTH_LONG).show();

                    GenericFragment f = AllenamentoFragment.getInstance();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, f.getFragment());
                    fragmentTransaction.commit();

                }




            }
        });

        return rootView;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }



}
