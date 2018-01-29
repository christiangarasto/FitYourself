package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

import ingsw.unical.it.fityourself.Model.Allenamento;
import ingsw.unical.it.fityourself.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EserciziFragment extends Fragment implements GenericFragment{
    View rootView;

    LinkedList<Allenamento> allenamentiSalvati;
    ListView lw_allenamentiSalvati;
    String userId;

    private Button btnEffettuaAllenamento;
    private Button btnAnnullaScelta;
    private static EserciziFragment eserciziFragment = null;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private static Allenamento daEffettuare;

    private EserciziFragment() {
        allenamentiSalvati = new LinkedList<Allenamento>();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Allenamenti");
        userId = FirebaseAuth.getInstance().getUid();
    }

    public static EserciziFragment getInstance(){
        if(eserciziFragment == null)
            eserciziFragment = new EserciziFragment();

        return eserciziFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Effettua esercizi");

        rootView = inflater.inflate(R.layout.fragment_esercizi, container, false);

        lw_allenamentiSalvati = (ListView) rootView.findViewById(R.id.lw_eserciziSalvatiDaScegliere);
        btnEffettuaAllenamento = (Button) rootView.findViewById(R.id.btnEffettuaAllenamento);
        btnAnnullaScelta = (Button) rootView.findViewById(R.id.btnAnnullaScelta);

        aggiungiAllenamentiSalvati();
        btnEffettuaAllenamento.setEnabled(false);

        lw_allenamentiSalvati.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               daEffettuare = (Allenamento) allenamentiSalvati.get(position);
               btnEffettuaAllenamento.setEnabled(true);
                Log.e("DEBUG:::::::::::::   ", daEffettuare.toString());
            }
        });

        btnEffettuaAllenamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericFragment dati = AllenamentoInCorsoFragment.getInstance();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, dati.getFragment());
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

    private void aggiungiAllenamentiSalvati() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Allenamenti").child(userId);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allenamentiSalvati.clear();

                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Allenamento allenamento = d.getValue(Allenamento.class);
                    allenamentiSalvati.add(allenamento);

                }

                LinkedList<String> allenamentiString = new LinkedList<String>();

                for (Allenamento a : allenamentiSalvati) {
                    allenamentiString.add(a.toString());
                }

                if (getContext() != null) {
                    StableArrayAdapter ad = new StableArrayAdapter(getContext(), android.R.layout.simple_list_item_1, allenamentiString);
                    lw_allenamentiSalvati.setAdapter(ad);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static Allenamento getDaEffettuare() {
        return daEffettuare;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
