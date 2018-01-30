package ingsw.unical.it.fityourself.MenuActivities.Fragments;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
    private static int posizione;
    private int numeroDurata;
    private String nomeDurata;
    private static ArrayList<String> serie;
    private ArrayAdapter <String> adapter;
    private static boolean [] effettuato;


    private AllenamentoInCorsoFragment(){
        this.posizione = 0;
        this.serie = new ArrayList();

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
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, serie);
        listView.setAdapter(adapter);

        String nome = getNomeDurata();

        if(nome.equals("serie")) {
            serie.clear();
            if (getNumeroDurata() > 0) {
                for (int durata = 0; durata < getNumeroDurata(); durata++) {
                    int d = durata + 1;
                    serie.add("SERIE N°: " + d);
                }
            }
        }
        else
        {
            serie.clear();
            int d = getNumeroDurata();
            serie.add(d + " " + nome);
        }

        adapter.notifyDataSetChanged();

        effettuato = new boolean[EserciziFragment.getDaEffettuare().getEsercizi().size()];

        nomeEsercizio.setText(EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getNomeEsercizio());
        durata.setText(EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getDurata());

        if(posizione == 0)
            precBtn.setText("esci");

        succBtn.setEnabled(false);

        succBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(Integer.toString(posizione)+ "---> ",Boolean.toString(effettuato[posizione]));

                if(posizione < EserciziFragment.getDaEffettuare().getEsercizi().size() && effettuato[posizione])
                    succBtn.setEnabled(true);

                if(!succBtn.getText().equals("salva")){
                        posizione++;
                        precBtn.setText("prec");

                    if(posizione < EserciziFragment.getDaEffettuare().getEsercizi().size() && !effettuato[posizione])
                    {
                        nomeEsercizio.setText(EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getNomeEsercizio());
                        durata.setText(EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getDurata());
                    }

                    if(posizione >= EserciziFragment.getDaEffettuare().getEsercizi().size())
                    {
                        succBtn.setText("salva");
                        precBtn.setEnabled(false);
                        succBtn.setEnabled(true);
                        nomeEsercizio.setText("Allenamento");
                        durata.setText("Terminato!");
                        durata.setTextColor(Color.GREEN);
                        nomeEsercizio.setTextColor(Color.GREEN);
                        serie.clear();

                        serie.add(EserciziFragment.getDaEffettuare().getNomeAllenamento() + "\n" + EserciziFragment.getDaEffettuare().getEsercizi());
                        adapter.notifyDataSetChanged();
                        // visualizzare gli allenamenti effettuati
                    }
                    else if(!effettuato[posizione]){

                        String nome1 = getNomeDurata();
                        if (nome1.equals("serie")) {
                            serie.clear();
                            if (getNumeroDurata() > 0) {
                                for (int durata = 0; durata < getNumeroDurata(); durata++) {
                                    int d = durata + 1;
                                    serie.add("SERIE N°: " + d);
                                }
                            }
                        } else {
                            serie.clear();
                            int d = getNumeroDurata();
                            serie.add(d + " " + nome1);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    //IMPLEMENTARE IL SALVATAGGIO
                }

                precBtn.setEnabled(true);

                if(!succBtn.getText().equals("salva"))
                    succBtn.setEnabled(false);
                if(posizione < EserciziFragment.getDaEffettuare().getEsercizi().size() && effettuato[posizione])
                    succBtn.setEnabled(true);
            }
        });

        precBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(precBtn.getText().equals("esci")){
                    Toast.makeText(getContext(), "Allenamento annullato!", Toast.LENGTH_SHORT).show();
                    GenericFragment dati = EserciziFragment.getInstance();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, dati.getFragment());
                    fragmentTransaction.commit();
                }
                else {
                    precBtn.setText("prec");

                    if (posizione == 1)
                        precBtn.setText("esci");
                    if (posizione >= 0) {
                        posizione--;
                        nomeEsercizio.setText(EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getNomeEsercizio());
                        durata.setText(EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getDurata());
                        succBtn.setText("succ");
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                serie.remove(position);
                if(!serie.isEmpty())
                    succBtn.setEnabled(false);
                else{
                    succBtn.setEnabled(true);
                    serie.add("ESERCIZIO COMPLETATO");
                    effettuato[posizione] = true;
                }
                adapter.notifyDataSetChanged();
            }
        });


        return rootView;
    }

    public int getNumeroDurata() {

        String tmp = "";

        tmp += Character.toString(EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getDurata().charAt(0));
        if(!Character.toString(EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getDurata().charAt(1)).equals(" "))
            tmp += Character.toString(EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getDurata().charAt(1));

        numeroDurata = Integer.parseInt(tmp);

        return numeroDurata;
    }

    public String getNomeDurata() {

        if(EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getDurata().charAt(2) == 's' || EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getDurata().charAt(3) == 's' || EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getDurata().charAt(4) == 's')
            nomeDurata = "serie";
        else if(EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getDurata().charAt(2) == 'm' || EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getDurata().charAt(3) == 'm' || EserciziFragment.getDaEffettuare().getEsercizi().get(posizione).getDurata().charAt(4) == 'm')
            nomeDurata = "minuti";

        return nomeDurata;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
