package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ingsw.unical.it.fityourself.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CorsaFragment extends Fragment implements GenericFragment{

    View rootView;

    public CorsaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Effettua corsa");

        rootView = inflater.inflate(R.layout.fragment_corsa, container, false);

        Button b = (Button) rootView.findViewById(R.id.corsa);

        return rootView;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
