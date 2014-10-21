//Kyle Kauck

package com.example.kyle.mapping;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class DisplayMap extends MapFragment implements LocationListener {

    GoogleMap mMap;
    LocationManager mLocManager;

    private static final int REQUEST_ENABLE_GPS = 0x01010;

    double mLatitude;
    double mLongitude;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mLocManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

    }

    private void enableLocation(){

        if (mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);

            Location location = mLocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null){

                mLatitude = location.getLatitude();
                mLongitude = location.getLongitude();

            }

        } else {

            new AlertDialog.Builder(getActivity()).setTitle("GPS Not Available").setMessage("Please Enable GPS In System Settings").setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent settings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(settings, REQUEST_ENABLE_GPS);

                }

            }).show();

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        enableLocation();

    }

    @Override
    public void onResume() {

        super.onResume();

        enableLocation();

    }

    @Override
    public void onPause() {

        super.onPause();

        mLocManager.removeUpdates(this);

    }

    @Override
    public void onLocationChanged(Location location) {

        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
