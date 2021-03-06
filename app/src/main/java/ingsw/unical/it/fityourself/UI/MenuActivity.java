package ingsw.unical.it.fityourself.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import ingsw.unical.it.fityourself.R;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    GenericFragment fragment;

    private static boolean daGestione = true;

    public static boolean isDaGestione() {
        return daGestione;
    }

    public static void setDaGestione(boolean daGestione) {
        MenuActivity.daGestione = daGestione;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set the fragment initially
        //fragment = AllenamentoFragment.getInstance();
        fragment = new ProgressBar();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment.getFragment());
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_allenati) {
            fragment = AllenamentoFragment.getInstance();
        } else if (id == R.id.nav_storicoAllenamenti) {
            fragment = StoricoAllenamentiFragment.getInstance();
        } else if (id == R.id.nav_consigliAlimentari) {
            fragment = new ConsigliAlimentariFragment();
        } else if (id == R.id.nav_datipersonali) {
            fragment = DatiPersonaliFragment.getInstance();
        } else if (id == R.id.nav_logout) {
            DatiPersonaliFragment.getInstance().setInstance();
            AllenamentoFragment.getInstance().setInstance();
            EserciziFragment.getInstance().setInstance();
            AggiuntaAllenamentoFragment.getInstance().setInstance();
            AllenamentoInCorsoFragment.getInstance().setInstance();
            GestisciEserciziFragment.getInstance().setInstance();
            NotificheFragment.getInstance().setInstance();
            StoricoAllenamentiFragment.getInstance().setInstance();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MenuActivity.this, LoginActivity.class));
            finish();
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment.getFragment());
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
