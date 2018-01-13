package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ingsw.unical.it.fityourself.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class GestisciEserciziFragment extends Fragment implements GenericFragment{

    View rootView;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    public GestisciEserciziFragment() {

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


        ScrollView contenitoreEserciziSalvati = rootView.findViewById(R.id.contenitoreEserciziSalvati);


    /*    TextView t1 = new TextView(getContext());
            t1.setText("Prova 123");


            contenitoreEserciziSalvati.addView(t1);
*/
        return rootView;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
