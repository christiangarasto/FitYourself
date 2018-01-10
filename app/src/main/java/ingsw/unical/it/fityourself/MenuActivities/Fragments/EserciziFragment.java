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
public class EserciziFragment extends Fragment implements GenericFragment{

    private View rootView;

    public EserciziFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Effettua esercizi");

        rootView = inflater.inflate(R.layout.fragment_esercizi, container, false);

        return rootView;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
