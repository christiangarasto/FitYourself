package ingsw.unical.it.fityourself.MenuActivities.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import ingsw.unical.it.fityourself.Model.Esercizio;
import ingsw.unical.it.fityourself.R;

/**
 * Created by valentina on 28/01/2018.
 */

public class AllenamentoInCorsoFragment extends Fragment implements GenericFragment{

    private View rootView;
    private static AllenamentoInCorsoFragment allenamentoFragment = null;
    private Button succBtn, precBtn;
    private ListView listView;
    private TextView nomeEsercizio, durata;


    private AllenamentoInCorsoFragment(){

    }

    public static AllenamentoInCorsoFragment getInstance(){
        if(allenamentoFragment == null)
            allenamentoFragment = new AllenamentoInCorsoFragment();

        return allenamentoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Esercizio In Corso");
        rootView = inflater.inflate(R.layout.fragment_allenamento_in_corso, container, false);

        succBtn = (Button) rootView.findViewById(R.id.succ);
        precBtn = (Button) rootView.findViewById(R.id.prec);
        listView = (ListView) rootView.findViewById(R.id.list);
        nomeEsercizio = (TextView) rootView.findViewById(R.id.nome);
        durata = (TextView) rootView.findViewById(R.id.durata);



        return rootView;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
