package com.example.shakil.currentlocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    double mLatitude;
    double mLongitude;
    LatLng currentLatLng;


    private static final String TAG = MapsActivity.class.getSimpleName();

    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;

    private String mLatitudeLabel;
    private String mLongitudeLabel;
    private TextView mLatitudeText;
    private TextView mLongitudeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toast.makeText(this,"onCreate",Toast.LENGTH_LONG).show();

        mLatitudeText = (TextView) findViewById((R.id.txt_latitude));
        mLongitudeText = (TextView) findViewById((R.id.txt_longitude));

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Add a marker in Sydney and move the camera

        Toast.makeText(this,"onMap",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(this,"onStart",Toast.LENGTH_LONG).show();
        getLastLocation();
    }


    @SuppressWarnings("MissingPermission")
    private void getLastLocation(){
        Toast.makeText(this,"getLocation",Toast.LENGTH_LONG).show();
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    // Task<Location> placeResult = mPlaceDetection
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null){
                            mLastLocation = task.getResult();

                            mLatitude = mLastLocation.getLatitude();
                            mLongitude = mLastLocation.getLongitude();

                            mLatitudeText.setText(String.format(Locale.ENGLISH,"%f",mLatitude));
                            mLongitudeText.setText(String.format(Locale.ENGLISH,"%f",mLongitude));

                            LatLng currentLocation = new LatLng(mLatitude,mLongitude);
                            mMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker in Sydney"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        }
                        else {
                            Log.w(TAG,"getLastLocation:exception",task.getException());
                        }
                    }
                });
    }


}
