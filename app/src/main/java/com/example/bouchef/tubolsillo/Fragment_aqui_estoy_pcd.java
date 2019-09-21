package com.example.bouchef.tubolsillo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;

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
        View vista = inflater.inflate(R.layout.activity_aqui_estoy_pcd, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear(); //clear old markers


                /*mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.4219999, -122.0862462))
                        .title("Spider Man")
                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.location)));

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.4629101,-122.2449094))
                        .title("Iron Man")
                        .snippet("His Talent : Plenty of money"));

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.3092293,-122.1136845))
                        .title("Captain America"));*/

                /*mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.3092293,-122.1136845))
                        .title("Captain America"));*/

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

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(loc.getLatitude(),loc.getLongitude()))
                            .title("AQUI ESTOY"));

                    CameraPosition googlePlex = CameraPosition.builder()
                            .target(new LatLng(loc.getLatitude(),loc.getLongitude()))
                            .zoom(20)
                            .bearing(0)
                            .tilt(45)
                            .build();

                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

                    //tvLatitud.setText(String.valueOf(loc.getLatitude()));
                    //tvLongitud.setText(String.valueOf(loc.getLongitude()));
                    //tvAltura.setText(String.valueOf(loc.getAltitude()));
                    //tvPrecision.setText(String.valueOf(loc.getAccuracy()));
                }
            }
        });


        return vista;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng buenos_aires = new LatLng(-34.6261192, -58.5741683);
        mMap.addMarker(new MarkerOptions().position(buenos_aires).title("Obelisco Buenos Aires"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(buenos_aires));
    }
}

