package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import ingsw.unical.it.fityourself.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GestisciEserciziFragment extends Fragment implements GenericFragment{

    View rootView;

    public GestisciEserciziFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Gestisci Esercizi");

        rootView = inflater.inflate(R.layout.fragment_gestisci_esercizi, container, false);

        ScrollView contenitoreEserciziSalvati = rootView.findViewById(R.id.contenitoreEserciziSalvati);


        TextView t1 = new TextView(getContext());
            t1.setText("Prova 123");


            contenitoreEserciziSalvati.addView(t1);

        return rootView;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
