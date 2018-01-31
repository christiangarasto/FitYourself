package ingsw.unical.it.fityourself.UI;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.TooltipCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roomorama.caldroid.CaldroidFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import ingsw.unical.it.fityourself.DOMAIN.Allenamento;
import ingsw.unical.it.fityourself.DOMAIN.Esercizio;
import ingsw.unical.it.fityourself.DOMAIN.Obiettivo;
import ingsw.unical.it.fityourself.DOMAIN.User;
import ingsw.unical.it.fityourself.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoricoAllenamentiFragment extends Fragment implements GenericFragment{

    View rootView;

    private static StoricoAllenamentiFragment storico = null;

    PopupWindow popupWindow;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    String userId;

    int anno;
    int mese;
    int giorno;
    LinkedList<Allenamento> allenamentiGiorno;

    CalendarView cal;

    private StoricoAllenamentiFragment() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Allenamenti Effettuati");
        userId = FirebaseAuth.getInstance().getUid();
        allenamentiGiorno = new LinkedList<Allenamento>();
    }

    public static StoricoAllenamentiFragment getInstance(){
        if(storico == null){
            storico = new StoricoAllenamentiFragment();
        }
        return storico;
    }
    //calendarioAllenamenti

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Storico allenamenti");
        rootView = inflater.inflate(R.layout.fragment_storico_allenamenti, container, false);

        final ListView allenamentiPerGiorno = (ListView) rootView.findViewById(R.id.allenamentipergiorno);
        cal = (CalendarView) rootView.findViewById(R.id.calendarioAllenamenti);

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, final int i, int i1, int i2) {

                anno = i;
                mese = i1; //mese numerico meno 1
                giorno = i2;

                //Toast.makeText(getContext(), i + ":" +i1 + ":" + i2, Toast.LENGTH_LONG).show();

                allenamentiGiorno.clear();

                mFirebaseDatabase.child(userId);
                    mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            if(dataSnapshot.getValue() != null) {
                                boolean almenoUno = false;
                                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                                    Date d = itemSnapshot.child("data").getValue(Date.class);

                                        //Log.e("STAMPA DATA::", d.getDate() + "-" + d.getMonth() + "-" + d.getYear());
                                        if(     d.getDate() == giorno &&
                                                d.getMonth() == mese &&
                                                d.getYear() + 1900 == anno){
                                            almenoUno = true;

                                            Allenamento a = new Allenamento();
                                                a.setNomeAllenamento(itemSnapshot.child("nomeAllenamento").getValue().toString());

                                            Object durata = itemSnapshot.child("durata").getValue();

                                            if(durata != null) {
                                                //Log.e("TROVO allenamento:", durata.toString());
                                                ArrayList<Obiettivo> ob = (ArrayList<Obiettivo>)itemSnapshot.child("obiettivi").getValue();
                                                Log.e("Ob::", ob.toString());
                                                a.setObiettivi(ob);
                                                a.setDurata(durata.toString());

                                            }else{
                                                //Log.e("TROVO allenamento:", "Durata non esistente per questo allenamento");

                                                    LinkedList<HashMap<String, String>> array = new LinkedList<HashMap<String, String>>();
                                                    HashMap<String, String> hashe = new HashMap<>();
                                                        hashe.put("nomeAllenamento", "Allenamento prova 1");
                                                        hashe.put("durata", "100 serie da 1000 ripetizioni");
                                                    array.add(hashe);

                                                    HashMap<String, String> hashe1 = new HashMap<>();
                                                        hashe1.put("nomeAllenamento", "Allenamento prova 2");
                                                        hashe1.put("durata", "100 serie da 1500 ripetizioni");
                                                    array.add(hashe1);

                                                    Log.e("Stampa hm[string]:", array.toString());

                                                ArrayList<Esercizio> es = (ArrayList<Esercizio>)itemSnapshot.child("esercizi").getValue();
                                                Log.e("Es::", es.toString());
                                                a.setEsercizi(es);
                                            }

                                            allenamentiGiorno.add(a);
                                            Log.e("Aggiungo allenamento::", a.toString());
                                        }

                                            ArrayList<String> all = new ArrayList<String>();

                                            if(!almenoUno){
                                                all.add("Nessun allenamento per questa data.");
                                            }else {
                                                for (Allenamento a : allenamentiGiorno) {

                                                    LinkedList<Esercizio> eserci = a.getEsercizi();
                                                    LinkedList<Obiettivo> obietti = a.getObiettivi();

                                                    String alle = a.getNomeAllenamento() + "\n";

                                                    if(a.getDurata() != null){
                                                        alle += "Corsa\n";
                                                    }else{
                                                            Log.e("Esercizi::: ", eserci.toString());
                                                        for(int i = 0; i < eserci.size(); i++) {
                                                            Esercizio e = null;
                                                            e.setDurata(a.getEsercizi().get(i).getDurata());
                                                        }
                                                    }

                                                    all.add(alle);

                                                }
                                            }
                                            final StableArrayAdapter sad = new StableArrayAdapter(getContext(), android.R.layout.simple_list_item_1, all);
                                            allenamentiPerGiorno.setAdapter(sad);

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
            }
        });

        return rootView;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
