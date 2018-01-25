package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import ingsw.unical.it.fityourself.Model.Notify;
import ingsw.unical.it.fityourself.Model.User;
import ingsw.unical.it.fityourself.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CorsaFragment extends Fragment implements GenericFragment,SensorEventListener{

    View rootView;

    int nPassi = 0;
    int distanza = 0;
    double calorie = 0;
    float passiSistema;
    boolean pausa = true;
    boolean primavolta = true;
        long _tempo = 0;

    Chronometer tempo;
    TextView passiTW;
    TextView calorieTW;
    TextView distanzaTW;
    TextView valorePassi;
    TextView valoreCalorie;
    TextView valoreDistanza;

    SensorManager sensorManager;
    boolean running = false;

    String userId;
    private DatabaseReference mFirebaseDatabaseDati;
    private DatabaseReference mFirebaseDatabaseNotifiche;
    private FirebaseDatabase mFirebaseInstance;
    double altezza = 0;
    double peso = 0;

    boolean abilita, intermedie, finale, anomalie;
    String tipoNotificaIntermedia;
    int valoreNotificaIntermedia;

    public CorsaFragment() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabaseDati = mFirebaseInstance.getReference("Dati Personali");
        mFirebaseDatabaseNotifiche = mFirebaseInstance.getReference("Notifiche");
        userId = FirebaseAuth.getInstance().getUid();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Effettua corsa");

        rootView = inflater.inflate(R.layout.fragment_corsa, container, false);

       tempo = (Chronometer) rootView.findViewById(R.id.tempo);
            tempo.setFormat("Tempo trascorso: %s");

            _tempo = 0;

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

                    //PASSAGGIO AL FRAGMENT PER VISUALIZZARE I PROGRESSI RAGGIUNTI

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
        valorePassi = (TextView) rootView.findViewById(R.id.numeropassi);
            valorePassi.setText(Integer.toString(nPassi));

        distanzaTW = (TextView) rootView.findViewById(R.id.distanzatw);
        valoreDistanza = (TextView) rootView.findViewById(R.id.distanzapercorsa);
        valoreDistanza.setText(Integer.toString(distanza));

        calorieTW = (TextView) rootView.findViewById(R.id.calorietw);
        valoreCalorie = (TextView) rootView.findViewById(R.id.caloriebruciate);
        valoreCalorie.setText(Double.toString(calorie));


        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        mFirebaseDatabaseDati.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.getValue() != null) {

                    User user = dataSnapshot.getValue(User.class);
                    if(dataSnapshot.getKey().equals(userId)){
                        altezza = user.getAltezza();
                        peso = user.getPeso();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mFirebaseDatabaseNotifiche.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.getValue() != null){
                    Notify notifiche = dataSnapshot.getValue(Notify.class);
                    if(dataSnapshot.getKey().equals(userId)){
                        abilita = notifiche.isAbilita();
                        intermedie = notifiche.isIntermedio();
                            if(intermedie){
                                if(!notifiche.getUnitaDiMisura().equals("_____") && notifiche.getValoreIntermedio() > 0){
                                    tipoNotificaIntermedia = notifiche.getUnitaDiMisura();
                                    valoreNotificaIntermedia = notifiche.getValoreIntermedio();
                                }else{
                                    intermedie = false;
                                }
                            }
                        finale = notifiche.isFinale();
                        anomalie = notifiche.isAnomalie();
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        })

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

        if(running && !pausa){
            if(primavolta){
                passiSistema = sensorEvent.values[0];
                primavolta = false;
            }

            valorePassi.setText(String.valueOf((int) (sensorEvent.values[0] - passiSistema)));

            int passi = Integer.parseInt(valorePassi.getText().toString());
            Log.e("DEBUG::: PASSI ", Integer.toString(passi));

            Double quartoAltezza = altezza/4;
            Log.e("DEBUG::: ALTEZZA ", Double.toString(quartoAltezza));

            Double distanza = passi * quartoAltezza;

            int d = distanza.intValue();
            Log.e("DEBUG::: DISTANZA ", Double.toString(distanza));

            valoreDistanza.setText(Integer.toString(d));


            double calorieBruciate = ((7000/3600) * tempo.getBase()) * peso;

            valoreCalorie.setText(Double.toString(calorieBruciate));



        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
