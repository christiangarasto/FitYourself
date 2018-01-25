package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ingsw.unical.it.fityourself.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoricoAllenamentiFragment extends Fragment implements GenericFragment{

    private static StoricoAllenamentiFragment storicoAllenamentiFragment = null;

    private StoricoAllenamentiFragment() {
        // Required empty public constructor
    }

    public static StoricoAllenamentiFragment getInstance(){
        if(storicoAllenamentiFragment == null)
            storicoAllenamentiFragment = new StoricoAllenamentiFragment();

        return storicoAllenamentiFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        getActivity().setTitle("Storico allenamenti");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storico_allenamenti, container, false);
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
