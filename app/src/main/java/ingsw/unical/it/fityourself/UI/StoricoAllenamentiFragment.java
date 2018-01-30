package ingsw.unical.it.fityourself.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ingsw.unical.it.fityourself.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoricoAllenamentiFragment extends Fragment implements GenericFragment{

    View rootView;

    private static StoricoAllenamentiFragment storico = null;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    String userId;

    private StoricoAllenamentiFragment() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Allenamenti");
        userId = FirebaseAuth.getInstance().getUid();
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

        CalendarView cal = (CalendarView) rootView.findViewById(R.id.calendarioAllenamenti);


        return rootView;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
