package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ingsw.unical.it.fityourself.R;

public class ConsigliAlimentariFragment extends Fragment {


    public ConsigliAlimentariFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Toast.makeText(getContext(), "Consigli alimentari bla bla", Toast.LENGTH_LONG).show();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consigli_alimentari, container, false);
    }



}
