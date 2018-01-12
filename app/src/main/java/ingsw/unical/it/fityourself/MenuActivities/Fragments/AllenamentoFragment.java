package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import ingsw.unical.it.fityourself.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllenamentoFragment extends Fragment implements GenericFragment{

    View rootView;
    Button corsa;
    Button esercizi;
    Button gestioneEsercizi;

    public AllenamentoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Allenati");

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
                GenericFragment corsa = new CorsaFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.fragment_container, corsa.getFragment());
                fragmentTransaction.commit();
            }
        });

        esercizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenericFragment esercizi = new EserciziFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.fragment_container, esercizi.getFragment());
                fragmentTransaction.commit();
            }
        });

        gestioneEsercizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenericFragment gestioneEsercizi = new GestisciEserciziFragment();
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
