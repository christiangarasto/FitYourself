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

import java.util.LinkedList;

import ingsw.unical.it.fityourself.Model.Allenamento;
import ingsw.unical.it.fityourself.Model.Esercizio;
import ingsw.unical.it.fityourself.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AggiuntaAllenamentoFragment extends Fragment implements GenericFragment{

    View rootView;
    private TextView txtDetails;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String userId;
    EditText input_nome_allenamento;

    private LinkedList<String> eserciziDisponibili;
    private LinkedList<String> eserciziAggiunti;


    public AggiuntaAllenamentoFragment() {
        eserciziDisponibili = new LinkedList<String>();
        eserciziAggiunti = new LinkedList<String>();
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
        mFirebaseDatabase = mFirebaseInstance.getReference("Utente");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("FitYourSelf").setValue("Allenamenti");

        // app_title change listener
        mFirebaseInstance.getReference("FitYourSelf").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });


        userId = FirebaseAuth.getInstance().getUid();
        input_nome_allenamento = (EditText) rootView.findViewById(R.id.input_nome_allenamento);
        final ListView lw_eserciziAggiunti = (ListView) rootView.findViewById(R.id.lw_eserciziAggiunti);



        StableArrayAdapter arrayAdapter = new StableArrayAdapter(getContext(), android.R.layout.simple_list_item_1, eserciziDisponibili);

        ListView lw_esercizidisponibili = rootView.findViewById(R.id.lw_eserciziDisponibili);
        lw_esercizidisponibili.setAdapter(arrayAdapter);

        lw_esercizidisponibili.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Esercizio aggiunto.", Toast.LENGTH_LONG).show();

                eserciziAggiunti.add(item);

                StableArrayAdapter ad = new StableArrayAdapter(getContext(), android.R.layout.simple_list_item_1, eserciziAggiunti);
                lw_eserciziAggiunti.setAdapter(ad);
                ad.notifyDataSetChanged();

            }
        });

        Button btnSalva = (Button) rootView.findViewById(R.id.btnSalva);
            btnSalva.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListView eserciziAggiunti = (ListView) rootView.findViewById(R.id.lw_eserciziAggiunti);
                    final String nomeAllenamento = input_nome_allenamento.getText().toString();

                    LinkedList<Esercizio> esercizi = new LinkedList<Esercizio>();

                    for(int i = 0; i < eserciziAggiunti.getCount(); i++) {
                        //esercizi.add(eserciziAggiunti.getChildAt(i));
                    }

                    if (TextUtils.isEmpty(userId)) {
                        createAllenamento(nomeAllenamento, esercizi);
                    } else {
                        updateAllenamento(nomeAllenamento, esercizi);
                    }

                    GenericFragment gestisci = new GestisciEserciziFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, gestisci.getFragment());
                    transaction.commit();
                }
            });

        Button btnAnnulla = (Button) rootView.findViewById(R.id.btnAnnulla);



        return rootView;
    }

    private void createAllenamento(String name, LinkedList<Esercizio> esercizi) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            //userId = mFirebaseDatabase.push().getKey();
            userId = FirebaseAuth.getInstance().getUid();
        }

        Allenamento allenamento = new Allenamento(name, esercizi);

        mFirebaseDatabase.child(userId).setValue(allenamento);
            for(Esercizio e : allenamento.getEsercizi()){
                mFirebaseDatabase.child(userId).child(allenamento.getNomeAllenamento()).setValue(allenamento);
            }


        addAllenamentoChangeListener();
    }

    private void updateAllenamento(String nome, LinkedList<Esercizio> esercizi) {
        // updating the user via child nodes
/*
        if (!TextUtils.isEmpty(nome))
            mFirebaseDatabase.child(userId).child("nome").setValue(nome);

        if (!TextUtils.isEmpty(cognome))
            mFirebaseDatabase.child(userId).child("cognome").setValue(cognome);

        String kg = Double.toString(peso);

        if (!TextUtils.isEmpty(kg))
            mFirebaseDatabase.child(userId).child("peso").setValue(peso);

        String alt = Double.toString(altezza);
        if (!TextUtils.isEmpty(alt))
            mFirebaseDatabase.child(userId).child("altezza").setValue(altezza);

        String age = Integer.toString(eta);
        if (!TextUtils.isEmpty(age))
            mFirebaseDatabase.child(userId).child("eta").setValue(eta);

        if (!TextUtils.isEmpty(sesso))
            mFirebaseDatabase.child(userId).child("sesso").setValue(sesso);

        String attitudine = Boolean.toString(sport);
        if (!TextUtils.isEmpty(attitudine))
            mFirebaseDatabase.child(userId).child("sport").setValue(sport);
*/
    }

    private void addAllenamentoChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Allenamento allenamento = dataSnapshot.getValue(Allenamento.class);

                // Check for null
                if (allenamento == null) {
                    Log.e(TAG, "allenamento data is null!");
                    return;
                }

                Log.e(TAG, "allenamento data is changed!" + allenamento.getNomeAllenamento() + "\n" + allenamento.getEsercizi() + "\n");

                // Display newly updated name and email
                txtDetails.setText(allenamento.getNomeAllenamento() + "\n" + allenamento.getEsercizi() + "\n");

               // toggleButton();
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
