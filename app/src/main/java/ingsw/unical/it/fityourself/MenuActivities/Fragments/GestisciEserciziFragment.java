package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

    LinkedList<String> allenamentiSalvati;
    ListView lw_allenamentiSalvati;
    String userId;


    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    public GestisciEserciziFragment() {
        allenamentiSalvati = new LinkedList<String>();
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

        aggiungiAllenamentiSalvati();

        return rootView;
    }

    private void aggiungiAllenamentiSalvati() {

        DatabaseReference ref = mFirebaseDatabase.getRef();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> allenamenti = dataSnapshot.getChildren();

                for(DataSnapshot d : allenamenti){
                    String nomeAllenamento = (String) d.child(userId).child("nomeAllenamento").getValue();
                    //LinkedList<Esercizio> esercizi = (LinkedList<Esercizio>) d.child(userId).child("esercizi").getValue();

                    Log.e("DEBUG:::::::", "Nome allenamento: " + nomeAllenamento);
                   // Log.e("DEBUG:::::::", "Esercizi: " + esercizi.toString());

                   // Log.e("DEBUG:::::::", "Nome allenamento: " + nomeAllenamento + " ------ Esercizi: " + esercizi.toString());
                }


                //System.out.println("DEBUG:::: " + allenamento.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
