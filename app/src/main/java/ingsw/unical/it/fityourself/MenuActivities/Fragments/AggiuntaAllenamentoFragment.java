package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.app.LauncherActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import ingsw.unical.it.fityourself.Model.Allenamento;
import ingsw.unical.it.fityourself.Model.Esercizio;
import ingsw.unical.it.fityourself.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AggiuntaAllenamentoFragment extends Fragment implements GenericFragment{

    View rootView;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String userId;
    EditText input_nome_allenamento;

    private LinkedList<String> eserciziDisponibili;
    private LinkedList<String> eserciziAggiunti;
    LinkedList<String> esercizi;

    private static AggiuntaAllenamentoFragment aggiuntaAllenamento = null;

    private AggiuntaAllenamentoFragment() {
        eserciziDisponibili = new LinkedList<String>();
        eserciziAggiunti = new LinkedList<String>();
        esercizi = new LinkedList<String>();
    }

    public static AggiuntaAllenamentoFragment getInstance(){
        if(aggiuntaAllenamento == null)
            aggiuntaAllenamento = new AggiuntaAllenamentoFragment();

        return aggiuntaAllenamento;
    }

    private void initEserciziDisponibili(){

        Esercizio addominali = new Esercizio("Addominali", "3 serie da 10 ripetizioni");
        Esercizio squat = new Esercizio("Squat", "3 serie da 15 ripetizioni");
        Esercizio flessioni = new Esercizio("Flessioni", "3 serie da 15 ripetizioni");
        Esercizio affondi = new Esercizio("Affondi", "2 serie da 20 ripetizioni");
        Esercizio ciclette = new Esercizio("Ciclette", "15 minuti");
        Esercizio pressa = new Esercizio("Pressa", "3 serie da 10 ripetizioni");

        eserciziDisponibili.add(addominali.toString());
        eserciziDisponibili.add(squat.toString());
        eserciziDisponibili.add(flessioni.toString());
        eserciziDisponibili.add(affondi.toString());
        eserciziDisponibili.add(ciclette.toString());
        eserciziDisponibili.add(pressa.toString());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        getActivity().setTitle("Nuovo Allenamento");

        rootView = inflater.inflate(R.layout.fragment_aggiunta_allenamento, container, false);

        initEserciziDisponibili();

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Allenamenti");


        userId = FirebaseAuth.getInstance().getUid();
        input_nome_allenamento = (EditText) rootView.findViewById(R.id.input_nome_allenamento);
        final ListView lw_eserciziAggiunti = (ListView) rootView.findViewById(R.id.lw_eserciziAggiunti);
            lw_eserciziAggiunti.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    final String item = (String) parent.getItemAtPosition(position);
                    Toast.makeText(getContext(), "Esercizio rimosso.", Toast.LENGTH_SHORT).show();

                    eserciziAggiunti.remove(item);

                    StableArrayAdapter ad = new StableArrayAdapter(getContext(), android.R.layout.simple_list_item_1, eserciziAggiunti);
                    lw_eserciziAggiunti.setAdapter(ad);
                    ad.notifyDataSetChanged();

                }
            });


        StableArrayAdapter arrayAdapter = new StableArrayAdapter(getContext(), android.R.layout.simple_list_item_1, eserciziDisponibili);

        ListView lw_esercizidisponibili = rootView.findViewById(R.id.lw_eserciziDisponibili);
        lw_esercizidisponibili.setAdapter(arrayAdapter);

        lw_esercizidisponibili.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Esercizio aggiunto.", Toast.LENGTH_SHORT).show();

                eserciziAggiunti.add(item);

                StableArrayAdapter ad = new StableArrayAdapter(getContext(), android.R.layout.simple_list_item_1, eserciziAggiunti);
                lw_eserciziAggiunti.setAdapter(ad);
                ad.notifyDataSetChanged();

            }
        });

        Button btnCasuale = (Button) rootView.findViewById(R.id.btnCasuale);
            btnCasuale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int eserciziDaAggiungere = new Random().nextInt(10)+5;

                    for(int i = 0; i < eserciziDaAggiungere; i++){
                        int esercizioDaPrendere = new Random().nextInt(eserciziDisponibili.size());
                        esercizi.add(eserciziDisponibili.get(esercizioDaPrendere));
                        eserciziAggiunti.add(eserciziDisponibili.get(esercizioDaPrendere));
                    }

                    StableArrayAdapter ad = new StableArrayAdapter(getContext(), android.R.layout.simple_list_item_1, esercizi);
                    lw_eserciziAggiunti.setAdapter(ad);
                    ad.notifyDataSetChanged();


                }
            });

        Button btnSalva = (Button) rootView.findViewById(R.id.btnSalva);
            btnSalva.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String nomeAllenamento = input_nome_allenamento.getText().toString();

                    if(nomeAllenamento.length() != 0) {

                        LinkedList<Esercizio> esercizi = new LinkedList<Esercizio>();


                        if (eserciziAggiunti.size() > 0) {
                            for (String s : eserciziAggiunti) {
                                Esercizio e = creaEsercizio(s);
                                esercizi.add(e);
                            }
                        }

                        if(esercizi.size() > 0) {

                            createAllenamento(nomeAllenamento, esercizi);

                            GenericFragment gestisci = GestisciEserciziFragment.getInstance();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, gestisci.getFragment());
                            transaction.commit();
                        }else{
                            Toast.makeText(getContext(), "Aggiungere almeno un esercizio all'allenamento!", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Inserire il nome dell'allenamento!", Toast.LENGTH_LONG).show();
                    }
                }
            });

        Button btnAnnulla = (Button) rootView.findViewById(R.id.btnAnnulla);
            btnAnnulla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GenericFragment gestisci = GestisciEserciziFragment.getInstance();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, gestisci.getFragment());
                    transaction.commit();
                }
            });


        return rootView;
    }

    private Esercizio creaEsercizio(String s) {

        Log.e("DEBUG: ", s);

        String nomeEsercizio = "";
        String durata = "";

        int nSpazi = 0;
        int nDueP = 0;

        for(int i = 0; i < s.length(); i++){
            if(nDueP == 0){
                if(s.charAt(i) == ':'){
                    nDueP++;
                }else{
                    nomeEsercizio += s.charAt(i);
                }
            }else{

                if(nSpazi == 0){
                    if(s.charAt(i) == ' '){
                        nSpazi++;
                    }
                }else{
                    durata += s.charAt(i);
                }
            }
        }

        Esercizio esercizio = new Esercizio(nomeEsercizio, durata);
        return esercizio;
    }

    private void createAllenamento(String name, LinkedList<Esercizio> esercizi) {

        if (TextUtils.isEmpty(userId)) {
            userId = FirebaseAuth.getInstance().getUid();
        }

        Allenamento allenamento = new Allenamento(name, esercizi);

        mFirebaseDatabase.child(userId).child(allenamento.getNomeAllenamento()).setValue(allenamento);
        addAllenamentoChangeListener();
    }

    private void addAllenamentoChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Allenamento allenamento = dataSnapshot.getValue(Allenamento.class);
                        if(allenamento == null)
                        {
                            Log.e(TAG, "Allenamento data is null!");
                            return;
                        }
                Log.e(TAG, "Allenamento data is changed!");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }
    @Override
    public Fragment getFragment() {
        return this;
    }
}
