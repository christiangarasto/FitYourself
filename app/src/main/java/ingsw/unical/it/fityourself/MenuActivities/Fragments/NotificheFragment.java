package ingsw.unical.it.fityourself.MenuActivities.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ingsw.unical.it.fityourself.Model.Notify;
import ingsw.unical.it.fityourself.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificheFragment extends Fragment implements GenericFragment{

    private View rootView;
    private Switch inputAbilita, inputIntermedio, inputFinale, inputAnomalie;
    private Button btnSalva, btnTorna, btnEsci;
    private EditText checkIntermedio;
    private Spinner inputMisura;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String unita;
    private String notifyUser;
    private static NotificheFragment notificheFragment = null;


    private NotificheFragment() {
        // Required empty public constructor
    }

    public static NotificheFragment getInstance(){
        if(notificheFragment == null){
            return new NotificheFragment();
        }

        return notificheFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Notifiche");
        rootView = inflater.inflate(R.layout.fragment_notifiche, container, false);

        inputAbilita = (Switch) rootView.findViewById(R.id.AbilitaNotifiche);
        inputIntermedio = (Switch) rootView.findViewById(R.id.Intermedio);
        inputFinale = (Switch) rootView.findViewById(R.id.Finale);
        inputAnomalie = (Switch) rootView.findViewById(R.id.Anomalie);
        checkIntermedio = (EditText) rootView.findViewById(R.id.ObIntermedio);

        inputMisura = (Spinner) rootView.findViewById(R.id.UnitaDiMisura);
        inputMisura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unita = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inputMisura.setEnabled(false);

        btnSalva = (Button) rootView.findViewById(R.id.SalvaNotifiche);
        btnTorna = (Button) rootView.findViewById(R.id.Torna);
        btnEsci = (Button) rootView.findViewById(R.id.EsciNotifiche);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Notifiche");

        btnSalva.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean abilita;
                boolean intermedio;
                boolean finale;
                boolean anomalie;
                int valoreIntermedio;

                if(inputAbilita.isChecked()){
                    abilita = true;
                }
                else{
                    abilita = false;
                    unita = "NON SELEZIONATO";
                    valoreIntermedio = 0;
                }

                if(inputIntermedio.isChecked()){
                    intermedio = true;
                    valoreIntermedio = Integer.parseInt(checkIntermedio.getText().toString());
                }
                else{
                    intermedio = false;
                    unita = "NON SELEZIONATO";
                    valoreIntermedio = 0;
                }

                if(inputFinale.isChecked()){
                    finale = true;
                }
                else{
                    finale = false;
                }
                if(inputAnomalie.isChecked()){
                    anomalie = true;
                }
                else{
                    anomalie = false;
                }

                String unitaDiMisura = unita;

                if (TextUtils.isEmpty(notifyUser)) {
                    createNotify(abilita, intermedio, finale, anomalie, valoreIntermedio, unitaDiMisura);
                } else {
                    updateNotify(abilita, intermedio, finale, anomalie, valoreIntermedio, unitaDiMisura);
                }

            }
        });

        btnEsci.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                GenericFragment gf = new ConsigliAlimentariFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, gf.getFragment());
                ft.commit();

            }
        });

        btnTorna.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                GenericFragment gf = DatiPersonaliFragment.getInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, gf.getFragment());
                ft.commit();

            }
        });

        inputIntermedio.setClickable(false);
        inputFinale.setClickable(false);
        inputAnomalie.setClickable(false);

        inputAbilita.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputAbilita.isChecked()){
                    inputIntermedio.setClickable(true);
                    inputFinale.setClickable(true);
                    inputAnomalie.setClickable(true);

                }
                else
                {
                    inputIntermedio.setClickable(false);
                    inputFinale.setClickable(false);
                    inputAnomalie.setClickable(false);

                    inputIntermedio.setChecked(false);
                    inputFinale.setChecked(false);
                    inputAnomalie.setChecked(false);
                }
            }
        });

        inputIntermedio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputIntermedio.isChecked()){
                    checkIntermedio.setEnabled(true);
                    inputMisura.setEnabled(true);
                    inputMisura.setClickable(true);
                }
                else{
                    checkIntermedio.setText("");
                    checkIntermedio.setEnabled(false);

                    inputMisura.setEnabled(false);
                    inputMisura.setClickable(false);
                }
            }
        });

      mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                  Notify not = dataSnapshot.getValue(Notify.class);

                  inputAbilita.setChecked(not.isAbilita());
                  inputIntermedio.setChecked(not.isIntermedio());
                  inputFinale.setChecked(not.isFinale());
                  inputAnomalie.setChecked(not.isAnomalie());

                  if (not.isIntermedio()) {
                      checkIntermedio.setEnabled(true);
                      inputMisura.setEnabled(true);
                      inputMisura.setClickable(true);

                      String check = Integer.toString(not.getValoreIntermedio());
                      checkIntermedio.setText(check);

                      int position = 0;

                      if (not.getUnitaDiMisura().equals("Distanza"))
                          position = 1;
                      else if (not.getUnitaDiMisura().equals("Passi"))
                          position = 2;

                      else if (not.getUnitaDiMisura().equals("Calorie"))
                          position = 3;

                      inputMisura.setSelection(position);
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

        return rootView;
    }


    private void createNotify(boolean abilita, boolean intermedio, boolean finale, boolean anomalie, int valoreIntermedio, String unitaDiMisura) {

        if (TextUtils.isEmpty(notifyUser)) {
            notifyUser = FirebaseAuth.getInstance().getUid();
        }

        Notify notify = new Notify(abilita, intermedio, finale, anomalie, unitaDiMisura, valoreIntermedio);

        mFirebaseDatabase.child(notifyUser).setValue(notify);

        addUserChangeListener();
    }

    private void updateNotify(boolean abilita, boolean intermedio, boolean finale, boolean anomalie, int valoreIntermedio, String unitaDiMisura) {

        String enable = Boolean.toString(abilita);
        String checkpoint = Boolean.toString(intermedio);
        String obiettivo = Boolean.toString(finale);
        String warning = Boolean.toString(anomalie);
        String checkValue = Integer.toString(valoreIntermedio);


        if (!TextUtils.isEmpty(enable))
            mFirebaseDatabase.child(notifyUser).child("abilita").setValue(enable);
        if (!TextUtils.isEmpty(checkpoint))
            mFirebaseDatabase.child(notifyUser).child("intermedio").setValue(checkpoint);
        if (!TextUtils.isEmpty(obiettivo))
            mFirebaseDatabase.child(notifyUser).child("finale").setValue(obiettivo);
        if (!TextUtils.isEmpty(warning))
            mFirebaseDatabase.child(notifyUser).child("anomalie").setValue(warning);
        if (!TextUtils.isEmpty(checkValue))
            mFirebaseDatabase.child(notifyUser).child("valoreIntermedio").setValue(checkValue);
        if (!TextUtils.isEmpty(unitaDiMisura))
            mFirebaseDatabase.child(notifyUser).child("unitaDiMisura").setValue(unitaDiMisura);
    }

    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(notifyUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Notify notify = dataSnapshot.getValue(Notify.class);

                // Check for null
                if (notify == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
            });
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
