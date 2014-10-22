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
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class DisplayMap extends MapFragment implements LocationListener {

    GoogleMap mMap;
    LocationManager mLocManager;

    private static final int REQUEST_ENABLE_GPS = 0x01010;

    public static double mLatitude;
    public static double mLongitude;

    private ArrayList<DataHelper> mEventDetails;
    DataHelper mDataHelper;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mLocManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        mEventDetails = MainActivity.mEventDetails;

        addMarkers();

    }

    private void enableLocation(){

        if (mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);

            Location location = mLocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null){

                mLatitude = location.getLatitude();
                mLongitude = location.getLongitude();

                mapLocation();

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

    private void mapLocation(){

        GoogleMap map = getMap();
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitude, mLongitude), 10));

    }

    private void addMarkers(){

        mMap = getMap();

        for (int i = 0; i < mEventDetails.size(); i++){

            mDataHelper = mEventDetails.get(i);

            mMap.addMarker(new MarkerOptions().position(new LatLng(mDataHelper.getLat(), mDataHelper.getLong())).title(mDataHelper.getName()));

            Log.i("Hi", "Bye");

        }

    }

    private class MarkerAdapter implements GoogleMap.InfoWindowAdapter{

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

    }

}
