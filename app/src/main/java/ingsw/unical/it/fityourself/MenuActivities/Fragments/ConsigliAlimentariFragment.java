package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;

import ingsw.unical.it.fityourself.R;

public class ConsigliAlimentariFragment extends Fragment implements GenericFragment{

    LinkedList<TextView> genericTips;
    LinkedList<TextView> specificTips;

    ListView tips_layout;
    View rootView;

    String userId;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    public ConsigliAlimentariFragment() {
        genericTips = new LinkedList<TextView>();
        specificTips = new LinkedList<TextView>();

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Allenamenti");
        userId = FirebaseAuth.getInstance().getUid();
    }

        private void initTips(char type){
            LinkedList<String> s_tip = new LinkedList<>();
            int ntips = 0;

            if(type == 'g') {   /////////////////////CONSIGLI GENERICI
                ntips = 14;

                String s_tip0 = "La sola attività fisica non è sufficiente a far calare di peso" +
                                " se non si associa ad essa una corretta alimentazione.";
                s_tip.add(s_tip0);

                String s_tip1 = "Se hai intrapreso da poco uno sport, è importante che tu non stravolga la tua dieta," +
                        " ma che la adatti gradualmente a seconda dell'intensità e della frequenza dell'allenamento," +
                        " il fabbisogno calorico aumenterà e quindi dovrai riconsiderare le quantità di cibo da assumere" +
                        " quotidianamente!";
                s_tip.add(s_tip1);

                String s_tip2 = "Iniziare l'allenamento 2 ore dall'ultimo pasto principale, " +
                                "di cui quest'ultimo deve essere ricco di cereali e derivati come pasta, pane, riso e patate.";
                s_tip.add(s_tip2);

                String s_tip3 = "Si a frutta, verdura e pesce!";
                s_tip.add(s_tip3);

                String s_tip4 = "Il consumo di acqua è importante prima e dopo l'allenamento!";
                s_tip.add(s_tip4);

                String s_tip5 = "L'attività sportiva deve essere accompagnata da una dieta" +
                                " particolare solo se la si pratica a livello agonistico!";
                s_tip.add(s_tip5);

                String s_tip6 = "E' opportuno dare la preferenza ad alimenti che forniscono elevate quantità di energia" +
                                " con il minimo impegno dell'apparato digerente.";
                s_tip.add(s_tip6);

                String s_tip7 = "E' consigliabile non mangiare in vista dell'allenamento, alimenti grassi. " +
                                "\nLa conseguenza è un elevato senso di pesantezza durante l'allenamento!";
                s_tip.add(s_tip7);

                String s_tip8 = "Se la ginnastica si fa al mattino, " +
                                "la colazione deve essere composta da succo di frutta, thè, pane tostato.";
                s_tip.add(s_tip8);

                String s_tip9 = "Se si prevede di allenarsi durante la pausa di mezzogiorno, la dieta va divisa in due parti:\n" +
                                "Un pò di frutta a metà mattinata,\n" +
                                "Un pranzo leggero subito dopo l'attività fisica.";
                s_tip.add(s_tip9);

                String s_tip10 = "Il pasto dopo l'allenamento è chiamato PASTO DI RECUPERO," +
                                 " esso è importante per reintegrare le perdite idriche e di sali minerali.";
                s_tip.add(s_tip10);

                String s_tip11 = "L'utilizzo di bevande ad elevato contenuto di zuccheri semplici" +
                                 " ritardano notevolmente al reidratazione dopo l'attività fisica.";
                s_tip.add(s_tip11);

                String s_tip12 = "E' consigliabile evitare bevande alcoliche, anche quelle a bassa gradazione.";
                s_tip.add(s_tip12);


                String s_tip13 = "Curiosità sui TEMPI DI DIGESTIONE DEGLI ALIMENTI:" +
                        "\n- Fino a 30 minuti: Glucosio, fruttosio, miele, alcool" +
                        "\n- 30-60 minuti: Thè, caffè, latte magro, limonate" +
                        "\n- 60-120 minuti: Latte, formaggio magro, pane bianco, pesce cotto, purè di patate" +
                        "\n- 120-180 minuti: Carne magra, pasta cotta, omelette" +
                        "\n- 180-240 minuti: Formaggio, insalata verde, prosciutto, filetto ai ferri" +
                        "\n- 240-300 minuti: Bistecca ai ferri, torte, arrosti, lenticchie" +
                        "\n- 360 minuti: Tonno sott'olio, cetrioli, fritture, funghi" +
                        "\n- 480 minuti: Crauti, cavoli, sardine sott'olio";
                s_tip.add(s_tip13);
            }else{      ////////////////////////////////CONSIGLI SPECIFICI
                ntips = 8;

                String s_tip0 = "La dieta dello sportivo deve sempre contenere frutta e verdura (sia fresche che cotte)" +
                                " per garantire un apporto adeguato di acqua, sali minerali, vitamine e fibre.";
                s_tip.add(s_tip0);

                String s_tip1 = "Dopo un'ora dalla fine dell'allenamento, è consentito mangiare carboidrati complessi," +
                                " in quanto questi andranno direttamente nei muscoli e non si trasformeranno in grasso." +
                                "\nQuesto perchè compensano ciò che abbiamo consumato durante l'attività fisica: il glicogeno muscolare," +
                                " che deve essere rimpiazzato dopo l'allenamento.";
                s_tip.add(s_tip1);

                String s_tip2 = "Non introdurre durante e dopo l'allenamento \"bevande per sportivi\", esse contengono zuccheri semplici, aromi e coloranti." +
                                " Preferisci uno \"Sport drink fai da te\", mixando frutta e verdura di stagione potrai gustare una bevanda ricca di vitamine e di fibre " +
                                "utili per il tuo organismo";
                s_tip.add(s_tip2);

                String s_tip3 = "L'atleta non ha bisogno di quantità extra di proteine e grassi." +
                                "\nL'apporto medio di proteine 1,1 - 1,5 g per ogni kg di peso corporeo" +
                                " è sufficiente a mantenere il perfetto funzionamento delle masse muscolari.";
                s_tip.add(s_tip3);

                String s_tip4 = "Come fonte proteica si consigliano le uova perchè sono un alimento naturale al 100%." +
                                " Il valore biologico a livello proteico è eccellente ed in più contengono anche una buona " +
                                "fonte di vitamine ed una piccola dose di ferro.";
                s_tip.add(s_tip4);

                String s_tip5 = "Alimenti proteici poveri di grassi sono: latte scremato, yogurt, carne magra, pesce, legumi e soia.";
                s_tip.add(s_tip5);

                String s_tip6 = "Per coloro che svolgono attività agonistica, la dose giornaliera consigliata può salire fino a 1,7 g " +
                                "di proteine per ogni kg di peso corporeo!";
                s_tip.add(s_tip6);

                String s_tip7 = "Esempio di pasto pre-gara:" +
                                "\n- Pasta con sugo di pomodoro e olio di oliva;" +
                                "\n- Pollo o tacchino senza condimenti grassi;" +
                                "\n- Insalata mista;" +
                                "\n- Frutta fresca di stagione;" +
                                "\nEsempio di pasto post-gara:" +
                                "\n- Minestrone con verdure, legumi, patate e olio di oliva;" +
                                "\n- Carni magre;" +
                                "\n- Insalata mista, ortaggi e legumi;" +
                                "\n- Frutta fresca di stagione;" +
                                "\n- Acqua minerale ad alto contenuto salino;";
                s_tip.add(s_tip7);

            }


            for(int i = 0; i < ntips; i++) {
                    TextView tip = new TextView(getActivity());
                    tip.setText(s_tip.get(i));
                    if (type == 'g')
                        genericTips.add(tip);
                    else
                        specificTips.add(tip);

            }

        }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Consigli alimentari");

        rootView = inflater.inflate(R.layout.fragment_consigli_alimentari, container, false);
        tips_layout = (ListView) rootView.findViewById(R.id.tips);

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         initTips('g');
         initTips('s');

        /*
            Ricerca nel database di almeno un allenamento da parte dell'utente.
            Quindi userdidtraining è vero se almeno una volta ho fatto un allenamento
        */
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("name")) {
                    addTips(false);
                    Toast.makeText(getContext(), "ConsigliAlimentari::UserDidTraining", Toast.LENGTH_SHORT).show();
                }else{
                    addTips(true);
                    Toast.makeText(getContext(), "ConsigliAlimentari::UserDidn'tTraining", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        tips_layout = (ListView) rootView.findViewById(R.id.tips);
    }

    private void addTips(boolean userDidTraining){
        final ArrayList<String> list = new ArrayList<String>();

        if(userDidTraining){
            for (int i = 0; i < specificTips.size(); ++i) {
                list.add(specificTips.get(i).getText().toString());
            }
        }

        for (int i = 0; i < genericTips.size(); ++i) {
            list.add(genericTips.get(i).getText().toString());
        }


        final StableArrayAdapter adapter = new StableArrayAdapter(this.getContext(),android.R.layout.simple_list_item_1, list);
        tips_layout.setAdapter(adapter);

        tips_layout.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
            }
        });
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
