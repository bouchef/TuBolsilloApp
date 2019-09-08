package com.example.bouchef.tubolsillo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment_aqui_estoy_pcd extends Fragment implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private TextView tvLatitud, tvLongitud, tvAltura, tvPrecision;
    /*Se declara una variable de tipo LocationManager encargada de proporcionar acceso al servicio de localización del sistema.*/
    private LocationManager locManager;
    /*Se declara una variable de tipo Location que accederá a la última posición conocida proporcionada por el proveedor.*/
    private Location loc;

    public Fragment_aqui_estoy_pcd() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_fragment1, container, false);

        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
*/
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getContext(), "No se han definido los permisos necesarios.", Toast.LENGTH_LONG).show();

        }else
        {
            /*Se asigna a la clase LocationManager el servicio a nivel de sistema a partir del nombre.*/
            locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Toast.makeText(getContext(), String.valueOf(loc.getLatitude()), Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(), String.valueOf(loc.getLongitude()), Toast.LENGTH_LONG).show();
            //tvLatitud.setText(String.valueOf(loc.getLatitude()));
            //tvLongitud.setText(String.valueOf(loc.getLongitude()));
            //tvAltura.setText(String.valueOf(loc.getAltitude()));
            //tvPrecision.setText(String.valueOf(loc.getAccuracy()));
        }
        return vista;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

