package ingsw.unical.it.fityourself.UI;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ingsw.unical.it.fityourself.Manifest;
import ingsw.unical.it.fityourself.R;

/**
 * Created by valentina on 29/01/2018.
 */

public class MappaFragment extends Fragment implements GenericFragment, OnMapReadyCallback {

    MapView map;
    View rootView;
    GoogleMap gMap;
    static MappaFragment mappaFragment = null;

    private static LocationManager lm;
    private static double longitude;
    private static double latitude;


    Button indietro;

    private MappaFragment() {

    }

    public static MappaFragment getInstance() {
        if (mappaFragment == null)
            mappaFragment = new MappaFragment();

        return mappaFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        indietro = (Button) rootView.findViewById(R.id.indietro);
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericFragment dati = MappaFragment.getInstance();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, dati.getFragment());
                fragmentTransaction.commit();
            }
        });

        map = (MapView) rootView.findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);

        map.getMapAsync(this);

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        getLocation();


        return rootView;
    }
    public void getLocation(){
        if(ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            Log.e("non ho fatto un cazzo ", "ma proprio un cazzo");
        }
            else{
            Location location = lm.getLastKnownLocation(lm.NETWORK_PROVIDER);
                if(location != null){
                     latitude = location.getLatitude();
                     longitude = location.getLongitude();
                        Toast.makeText(getContext(), " Latitude : "+ latitude, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), " Longitude : "+ longitude, Toast.LENGTH_SHORT).show();
                }
                    else {
                        Toast.makeText(getContext(), " latitude not found ", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1 :
                getLocation();
                break;
        }
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng posAttuale = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(posAttuale)
                .title("SEI QUI"));
        googleMap.animateCamera(CameraUpdateFactory.zoomBy(20f));
        googleMap.setMinZoomPreference(15f);
       // googleMap.setMaxZoomPreference(20f);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(posAttuale));

    }

    @Override
    public void onResume() {
        map.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }
}
