package ingsw.unical.it.fityourself.AccountActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
//import com.firebaseloginapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ingsw.unical.it.fityourself.Model.User;
import ingsw.unical.it.fityourself.R;

/**
 * Created by valentina on 03/01/2018.
 */

public class DatiPersonaliActivity extends AppCompatActivity {

    private static final String TAG = DatiPersonaliActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputName, inputCognome, inputPeso, inputAltezza, inputEta;
    private RadioButton inputMaschio, inputFemmina;
    private Switch inputSport;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String sesso;
    private boolean sport;

    private String userId;

    private SignupActivity puntatore = new SignupActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dati_personali);

        // Displaying toolbar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        inputName = (EditText) findViewById(R.id.Nome);
        inputCognome = (EditText) findViewById(R.id.Cognome);
        inputPeso = (EditText) findViewById(R.id.Peso);
        inputAltezza = (EditText) findViewById(R.id.Altezza);
        inputEta = (EditText) findViewById(R.id.Eta);
        inputMaschio = (RadioButton) findViewById(R.id.Maschio);
        inputFemmina = (RadioButton) findViewById(R.id.Femmina);
        inputSport = (Switch) findViewById(R.id.Sport);


        btnSave = (Button) findViewById(R.id.Salva);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Utente");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("FitYourSelf").setValue("DatiUtente");

        // app_title change listener
        mFirebaseInstance.getReference("FitYourSelf").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        // Save / update the user
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
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
                } else {
                    updateUser(name, cognome, peso, altezza, eta, sesso, sport);
                }
            }
        });

        toggleButton();
    }

    // Changing button text
    private void toggleButton() {
        if (TextUtils.isEmpty(userId)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    }

    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String cognome,double peso,double altezza,int eta, String sesso, boolean sport) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        User user = new User(name, cognome, peso, altezza, eta, sesso, sport, puntatore.getMyEmail());

        mFirebaseDatabase.child(userId).setValue(user);

        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.getNome() + ", " + user.getCognome());

                // Display newly updated name and email
                txtDetails.setText(user.getNome() + ", " + user.getCognome());

                // clear edit text
                inputCognome.setText("");
                inputName.setText("");

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(String name, String cognome,double peso,double altezza,int eta, String sesso, boolean sport) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userId).child("nome").setValue(name);

        if (!TextUtils.isEmpty(cognome))
            mFirebaseDatabase.child(userId).child("cognome").setValue(cognome);
    }

    public void onRadioButtonClickeda(View view){
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
}
