package ingsw.unical.it.fityourself.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ingsw.unical.it.fityourself.DOMAIN.User;
import ingsw.unical.it.fityourself.R;

public class ProgressBar extends Fragment implements GenericFragment {

    private View rootView;
    private android.widget.ProgressBar progressBar;
    private boolean inseriti = false;
    private boolean nonPassarePiu = false;
    private static FirebaseDatabase mFirebaseInstance;
    private static DatabaseReference mFirebaseDatabase;

    public ProgressBar(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_progress_bar, container, false);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Dati Personali");

        controllaDatiPersonali();

        return rootView;
    }

    public void controllaDatiPersonali() {
        final long[] occorrenze = {0};

        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.getValue() != null) {
                    User userTmp = dataSnapshot.getValue(User.class);

                    if (dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())) {

                        if (!userTmp.getNome().equals("") &&
                                !userTmp.getCognome().equals("") &&
                                userTmp.getPeso() != 0 &&
                                userTmp.getAltezza() != 0 &&
                                userTmp.getEta() != 0 &&
                                !userTmp.getSesso().equals("")) {
                            setInseriti(true);
                        }

                        if (inseriti) {
                            if(!DatiPersonaliFragment.isDatiSalvati()) {
                                GenericFragment allenamento = AllenamentoFragment.getInstance();
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container, allenamento.getFragment());
                                fragmentTransaction.commit();
                                return;
                            }
                        }
                    }
                }
                if(!DatiPersonaliFragment.isDatiSalvati()) {
                        GenericFragment datiPersonali = DatiPersonaliFragment.getInstance();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, datiPersonali.getFragment());
                        fragmentTransaction.commit();
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

    public boolean isInseriti() {
        return inseriti;
    }

    public void setInseriti(boolean inseriti) {
        this.inseriti = inseriti;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}


