package ingsw.unical.it.fityourself.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ingsw.unical.it.fityourself.DOMAIN.User;
import ingsw.unical.it.fityourself.R;

public class DatiPersonaliFragment extends Fragment implements GenericFragment{

    private static final String TAG = DatiPersonaliFragment.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputNome, inputCognome, inputPeso, inputAltezza, inputEta;
    private RadioButton inputMaschio, inputFemmina;
    private Switch inputSport;
    private Button btnSave;

    public Button getBtnExit() {
        return btnExit;
    }

    public void setBtnExit(Button btnExit) {
        this.btnExit = btnExit;
    }

    private Button btnExit;
    private Button btnNotify;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String sesso;
    private boolean sport;


    View rootView;
    private String userId;// = FirebaseAuth.getInstance().getUid();
    private static DatiPersonaliFragment datiPersonali = null;

    private static boolean datiSalvati = false;

    private DatiPersonaliFragment() {
        // Required empty public constructor
    }

    public static DatiPersonaliFragment getInstance(){
        if(datiPersonali == null){
            datiPersonali = new DatiPersonaliFragment();
        }
        return datiPersonali;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        getActivity().setTitle("Dati personali");
        rootView = inflater.inflate(R.layout.fragment_dati_personali, container, false);

        inputNome = (EditText) rootView.findViewById(R.id.Nome);
        inputCognome = (EditText) rootView.findViewById(R.id.Cognome);
        inputPeso = (EditText) rootView.findViewById(R.id.Peso);
        inputAltezza = (EditText) rootView.findViewById(R.id.Altezza);
        inputEta = (EditText) rootView.findViewById(R.id.Eta);
        inputMaschio = (RadioButton) rootView.findViewById(R.id.Maschio);
        inputFemmina = (RadioButton) rootView.findViewById(R.id.Femmina);
        inputSport = (Switch) rootView.findViewById(R.id.Sport);


        btnSave = (Button) rootView.findViewById(R.id.Salva);
        btnExit = (Button) rootView.findViewById(R.id.Esci);
        btnNotify = (Button) rootView.findViewById(R.id.Notifiche);
        txtDetails = new TextView(getContext());

        txtDetails = new TextView(getContext());

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'Dati Personali' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Dati Personali");

        btnExit.setEnabled(false);

        if(!inputNome.getText().toString().equals("") && !inputCognome.getText().toString().equals("") &&
           !inputPeso.getText().toString().equals("") && !inputAltezza.getText().toString().equals("") &&
           !inputEta.getText().toString().equals("") && (inputMaschio.isChecked() || inputFemmina.isChecked())
          ){
            btnExit.setEnabled(true);
        }


        // Save / update the user
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputNome.getText().toString();
                String cognome = inputCognome.getText().toString();
                double peso = Double.parseDouble(inputPeso.getText().toString());
                double altezza = Double.parseDouble(inputAltezza.getText().toString());
                int eta = Integer.parseInt(inputEta.getText().toString());

                if(inputSport.isChecked())
                    sport = true;
                else
                    sport = false;
                // Check for already existed userId
                if (TextUtils.isEmpty(userId)) {
                    createUser(name, cognome, peso, altezza, eta, sesso, sport);
                    setDatiSalvati(true);
                    btnExit.setEnabled(true);
                } else {
                    updateUser(name, cognome, peso, altezza, eta, sesso, sport);
                    setDatiSalvati(true);
                    btnExit.setEnabled(true);
                }

               /*
               if(isDatiSalvati()){
                   GenericFragment allenamento = AllenamentoFragment.getInstance();
                   FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                   fragmentTransaction.replace(R.id.fragment_container, allenamento.getFragment());
                   fragmentTransaction.commit();
               }
               */

            }


        });

        inputMaschio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClicked(view);
            }
        });

        inputFemmina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClicked(view);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenericFragment gf = AllenamentoFragment.getInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, gf.getFragment());
                ft.commit();

            }
        });

        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenericFragment gf = NotificheFragment.getInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, gf.getFragment());
                ft.commit();
            }
        });

        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    User userTmp = dataSnapshot.getValue(User.class);

                if(dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())){

                        inputNome.setText(userTmp.getNome());

                        inputCognome.setText(userTmp.getCognome());

                        String peso = Double.toString(userTmp.getPeso());
                        String altezza = Double.toString(userTmp.getAltezza());
                        String eta = Integer.toString(userTmp.getEta());

                        inputPeso.setText(peso);
                        inputAltezza.setText(altezza);
                        inputEta.setText(eta);

                        if (userTmp.getSesso().equals("Maschio"))
                            inputMaschio.setChecked(true);
                        else
                            inputFemmina.setChecked(true);

                        inputSport.setChecked(userTmp.isSport());

                        btnExit.setEnabled(true);

                        setDatiSalvati(false);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void createUser(String name, String cognome,double peso,double altezza,int eta, String sesso, boolean sport) {

        if (TextUtils.isEmpty(userId)) {
            userId = FirebaseAuth.getInstance().getUid();
        }

        User user = new User(name, cognome, peso, altezza, eta, sesso, sport);
        mFirebaseDatabase.child(userId).setValue(user);
        addUserChangeListener();
    }

    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User userTmp = dataSnapshot.getValue(User.class);
                if (userTmp == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }
                Log.e(TAG, "User data is changed!" + userTmp.getNome() + "\n" + userTmp.getCognome() + userTmp.getPeso() + "\n"+ userTmp.getAltezza() + "\n"+ userTmp.getEta() + "\n"+ userTmp.getSesso() + "\n"+ userTmp.isSport() + "\n");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(String nome, String cognome,double peso,double altezza,int eta, String sesso, boolean sport) {
        Log.e(TAG,"update datiPersonali");
           if (!TextUtils.isEmpty(nome))
               mFirebaseDatabase.child(userId).child("nome").setValue(nome);

           if (!TextUtils.isEmpty(cognome))
               mFirebaseDatabase.child(userId).child("cognome").setValue(cognome);

           String kg = Double.toString(peso);

           if (!TextUtils.isEmpty(kg))
               mFirebaseDatabase.child(userId).child("peso").setValue(peso);

           String alt = Double.toString(altezza);
           if (!TextUtils.isEmpty(alt))
               mFirebaseDatabase.child(userId).child("altezza").setValue(altezza);

           String age = Integer.toString(eta);
           if (!TextUtils.isEmpty(age))
               mFirebaseDatabase.child(userId).child("eta").setValue(eta);

           if (!TextUtils.isEmpty(sesso))
               mFirebaseDatabase.child(userId).child("sesso").setValue(sesso);

           String attitudine = Boolean.toString(sport);
           if (!TextUtils.isEmpty(attitudine))
               mFirebaseDatabase.child(userId).child("sport").setValue(sport);

    }

    public void onRadioButtonClicked(View view){
        boolean selezionato = ((RadioButton) view).isChecked();

        switch(view.getId()){
            case R.id.Maschio:
                if(selezionato)
                    sesso = "Maschio";
                break;

            case R.id.Femmina:
                if(selezionato)
                    sesso = "Femmina";
                break;
        }
    }

    public static boolean isDatiSalvati() {
        return datiSalvati;
    }

    public static void setDatiSalvati(boolean datiSalvati) {
        DatiPersonaliFragment.datiSalvati = datiSalvati;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

}
