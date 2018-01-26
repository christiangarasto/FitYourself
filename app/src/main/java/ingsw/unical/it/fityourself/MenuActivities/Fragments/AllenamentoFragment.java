package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ingsw.unical.it.fityourself.Model.User;
import ingsw.unical.it.fityourself.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllenamentoFragment extends Fragment implements GenericFragment{

    View rootView;
    Button corsa;
    Button esercizi;
    Button gestioneEsercizi;

    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private static boolean datiInseriti;

    private static AllenamentoFragment allenamentoFragment = null;

    private AllenamentoFragment() {

    }

    public static AllenamentoFragment getInstance(){
        if(allenamentoFragment == null)
            allenamentoFragment = new AllenamentoFragment();

        return allenamentoFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Allenati");

        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'Dati Personali' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Dati Personali");

        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getValue() != null)
                {
                    User userTmp = dataSnapshot.getValue(User.class);

                    if(dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())){

                        if(     !userTmp.getNome().equals("") &&
                                !userTmp.getCognome().equals("") &&
                                userTmp.getPeso() != 0 &&
                                userTmp.getAltezza() != 0 &&
                                userTmp.getEta() != 0 &&
                                !userTmp.getSesso().equals("")){
                            datiInseriti = true;
                        }
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

        if(!datiInseriti)
        {
            Toast.makeText(getContext(), "Attenzione! devi inserire i dati personali.", Toast.LENGTH_LONG).show();

            GenericFragment dati = DatiPersonaliFragment.getInstance();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container, dati.getFragment());
            fragmentTransaction.commit();
        }


        rootView = inflater.inflate(R.layout.fragment_allenamento, container, false);
        corsa = (Button) rootView.findViewById(R.id.corsa);
        esercizi = (Button) rootView.findViewById(R.id.esercizi);
        gestioneEsercizi = (Button) rootView.findViewById(R.id.gestisciEsercizi);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        corsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenericFragment corsa = CorsaFragment.getInstance();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.fragment_container, corsa.getFragment());
                fragmentTransaction.commit();
            }
        });

        esercizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenericFragment esercizi = EserciziFragment.getInstance();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.fragment_container, esercizi.getFragment());
                fragmentTransaction.commit();
            }
        });

        gestioneEsercizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenericFragment gestioneEsercizi = GestisciEserciziFragment.getInstance();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.fragment_container, gestioneEsercizi.getFragment());
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
