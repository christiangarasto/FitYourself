package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import ingsw.unical.it.fityourself.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CorsaFragment extends Fragment implements GenericFragment,SensorEventListener{

    View rootView;

    int nPassi = 0;
    int distanza = 0;
    int calorie = 0;
    float passiSistema;
    boolean pausa = true;
    boolean miserve = true;
        long _tempo = 0;

    Chronometer tempo;
    TextView passiTW;
    TextView calorieTW;
    TextView distanzaTW;

    SensorManager sensorManager;
    boolean running = false;

    public CorsaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Effettua corsa");

        rootView = inflater.inflate(R.layout.fragment_corsa, container, false);

       tempo = (Chronometer) rootView.findViewById(R.id.tempo);
            tempo.setFormat("Tempo trascorso: %s");

            _tempo = (int) tempo.getBase() - (int) SystemClock.elapsedRealtime();

        EditText obiettivoPassi = (EditText) rootView.findViewById(R.id.obiettivopassi);
            obiettivoPassi.setHint("                      -");

        EditText obiettivoDistanza = (EditText) rootView.findViewById(R.id.obiettivodistanza);
            obiettivoDistanza.setHint("                      -");

        EditText obiettivoCalorie = (EditText) rootView.findViewById(R.id.obiettivocalorie);
            obiettivoCalorie.setHint("                      -");

        Button termina = (Button) rootView.findViewById(R.id.terminacorsabtn);
            termina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Termina", Toast.LENGTH_SHORT).show();
                }
            });

        final Button pausariprendi = (Button) rootView.findViewById(R.id.pausariprendibtn);
            pausariprendi.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    if(pausa){
                        pausariprendi.setText("Pausa");
                        //Toast.makeText(getContext(), "Riprendi", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getContext(), tempo.getContentDescription(), Toast.LENGTH_SHORT).show();
                        if(_tempo == 0){
                          tempo.setBase(SystemClock.elapsedRealtime());
                        }
                        else{
                            long intervallo = 0;
                            if(SystemClock.elapsedRealtime() > _tempo){
                                intervallo = SystemClock.elapsedRealtime() - _tempo;
                            }else{
                                intervallo =  _tempo - SystemClock.elapsedRealtime();
                            }
                                Toast.makeText(getContext(), "Minore di 0: " + Long.toString(intervallo), Toast.LENGTH_SHORT).show();
                            tempo.setBase(tempo.getBase() + intervallo);
                        }
                        tempo.start();
                        pausa = false;
                    }else{
                        pausariprendi.setText("Riprendi");
                        Toast.makeText(getContext(), "Pausa", Toast.LENGTH_SHORT).show();
                        tempo.stop();

                        _tempo = SystemClock.elapsedRealtime();
                        pausa = true;
                    }

                }
            });

        passiTW = (TextView) rootView.findViewById(R.id.passitw);
            passiTW.setText("Passi effettuati: " + Integer.toString(nPassi));

        distanzaTW = (TextView) rootView.findViewById(R.id.distanzatw);
            distanzaTW.setText("Distanza percorsa: " + Integer.toString(distanza) + " metri");

        calorieTW = (TextView) rootView.findViewById(R.id.calorietw);
            calorieTW.setText("Calorie bruciate: " + Integer.toString(calorie) + " cal");


        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);



        return rootView;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null){
            
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(getContext(), "Sensore di movimento non rilevato!\nI passi e la distanza percorsa non verranno aggiornati.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(running){
            if(miserve){
                passiSistema = sensorEvent.values[0];
                miserve = false;
            }

            passiTW.setText(String.valueOf("Passi effettuati: " + (int) (sensorEvent.values[0] - passiSistema)));

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
