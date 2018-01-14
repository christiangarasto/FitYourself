package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ingsw.unical.it.fityourself.Model.Allenamento;
import ingsw.unical.it.fityourself.Model.Esercizio;
import ingsw.unical.it.fityourself.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GestisciEserciziFragment extends Fragment implements GenericFragment{

    View rootView;

    LinkedList<Allenamento> allenamentiSalvati;
    ListView lw_allenamentiSalvati;
    String userId;


    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    public GestisciEserciziFragment() {
        allenamentiSalvati = new LinkedList<Allenamento>();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Allenamenti");
        userId = FirebaseAuth.getInstance().getUid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Gestisci Esercizi");

        rootView = inflater.inflate(R.layout.fragment_gestisci_esercizi, container, false);

        FloatingActionButton aggiungiAllenamento = (FloatingActionButton) rootView.findViewById(R.id.nuovoEsercizio);
            aggiungiAllenamento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GenericFragment aggiuntaAllenamento = new AggiuntaAllenamentoFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    fragmentTransaction.replace(R.id.fragment_container, aggiuntaAllenamento.getFragment());
                    fragmentTransaction.commit();
                }
            });

        lw_allenamentiSalvati = (ListView) rootView.findViewById(R.id.lw_allenamentiSalvati);
        lw_allenamentiSalvati.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                Allenamento a = allenamentiSalvati.get(position);
                mFirebaseDatabase.child(userId).child(a.getNomeAllenamento()).removeValue();
                mFirebaseDatabase.child(userId).child("esercizi").removeValue();
                Toast.makeText(getContext(), "Allenamento eliminato.", Toast.LENGTH_SHORT).show();
            }
        });
        aggiungiAllenamentiSalvati();

        return rootView;
    }

    private void aggiungiAllenamentiSalvati() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Allenamenti").child(userId);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allenamentiSalvati.clear(); //Forse non serve

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

    @Override
    public Fragment getFragment() {
        return this;
    }
}
