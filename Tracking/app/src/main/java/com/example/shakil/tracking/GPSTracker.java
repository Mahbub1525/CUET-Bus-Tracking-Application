package com.example.shakil.tracking;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by shakil on 19-Jan-18.
 */

public class GPSTracker extends Service implements LocationListener {

    private static String TAG = GPSTracker.class.getName();
    private final Context mContext;

    boolean isGPSEnabled = false;//GPS Status
    boolean isNetworkEnnabled = false; //Network Status
    boolean isGPSTrackingEnabled = false;//GPS Tracking Status

    Location location;
    double latitude;
    double longitude;

    int geocoderMaxResults = 1;

    private static final long minDistanceChangeForUpdates = 10; //10 meters
    private static final long minTimeBTWUpdates = 60000; //in miliseconds

    protected LocationManager locationManager;

    //LocationManager.GPS_Provider or LocationManager.Network_Proverder info
    private String providerInfo;


    public GPSTracker(Context mContext) {
        this.mContext = mContext;
        getLocation();
    }

    //function for getting current location
    public void getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            //getting network status
            isNetworkEnnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            //if gps enabled ,getting location
            if (isGPSEnabled) {
                this.isGPSTrackingEnabled = true;
                Log.d(TAG, "Using GPS Service");
                providerInfo = LocationManager.GPS_PROVIDER;
            } else if (isNetworkEnnabled) {
                this.isGPSTrackingEnabled = true;
                Log.d(TAG, "Using Network State to get GPS Coordinates");
                providerInfo = LocationManager.NETWORK_PROVIDER;
            }
            //Application can use GPS or Network Provider
            if (!providerInfo.isEmpty()) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    locationManager.requestLocationUpdates(providerInfo, minTimeBTWUpdates, minDistanceChangeForUpdates, this);

                }
            }
            if (locationManager != null){
                location = locationManager.getLastKnownLocation(providerInfo);
                updateGPSCoordinates();
            }
        }catch (Exception e){
            //e.printStackTrace();
            Log.e(TAG,"Impossible to connect to LocationManager",e);
        }
    }
    //function for updating latitude and longitude
    public void updateGPSCoordinates(){
        if (location != null){
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

    }
    /*
    GPSTracker latitude getter and setter
    return latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        return latitude;
    }
    /*
    GPSTracker longitude getter and setter
    return longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        return longitude;
    }
    /*
    GPSTracker isGPSTrackingEnabled getter
    checking GPS/wifi is enabled
     */
    public boolean getIsGPSTrackingEnabled() {

        return this.isGPSTrackingEnabled;
    }

    /*
    function to stop using GPS
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
