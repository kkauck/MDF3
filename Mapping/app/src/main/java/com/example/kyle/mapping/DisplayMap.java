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
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplayMap extends MapFragment implements LocationListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapLongClickListener {

    GoogleMap mMap;
    LocationManager mLocManager;

    private static final int REQUEST_ENABLE_GPS = 0x01010;
    private HashMap<String, DataHelper> mapInfo = new HashMap<String, DataHelper>();
    private final String EXTRASTRING = "Event_Details";

    public static double mLatitude;
    public static double mLongitude;

    private ArrayList<DataHelper> mEventDetails;
    DataHelper mDataHelper;

    int arrayLocation;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mLocManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        mEventDetails = MainActivity.mEventDetails;

    }

    private void enableLocation(){

        if (mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);

            Location myLocation = mLocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (myLocation != null){

                mLatitude = myLocation.getLatitude();
                mLongitude = myLocation.getLongitude();

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
        addMarkers();

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
        map.setOnMapLongClickListener(this);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitude, mLongitude), 10));

    }

    public void addMarkers(){

        mMap = getMap();

        for (int i = 0; i < mEventDetails.size(); i++){

            mDataHelper = mEventDetails.get(i);

            Marker map = mMap.addMarker(new MarkerOptions().position(new LatLng(mDataHelper.getLat(), mDataHelper.getLong())).title(mDataHelper.getName()).snippet(mDataHelper.getTime()));

            String key = map.getId();

            mapInfo.put(key, mDataHelper);

            mMap.setInfoWindowAdapter(new MarkerAdapter());
            mMap.setOnInfoWindowClickListener(this);

        }

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        String markerInfo = marker.getId();

        mDataHelper = mapInfo.get(markerInfo);

        Intent display = new Intent(getActivity(), DisplayActivity.class);
        display.putExtra(EXTRASTRING, mDataHelper);
        startActivity(display);

        marker.hideInfoWindow();

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        mLongitude = latLng.longitude;
        mLatitude = latLng.latitude;

        ((MainActivity) getActivity()).createNewData();

    }


    private class MarkerAdapter implements GoogleMap.InfoWindowAdapter{

        @Override
        public View getInfoWindow(Marker marker) {

            return null;

        }

        @Override
        public View getInfoContents(Marker marker) {

            View view = getActivity().getLayoutInflater().inflate(R.layout.infowindow, null);

            TextView mTitle = (TextView) view.findViewById(R.id.infoTitle);
            TextView mDate = (TextView) view.findViewById(R.id.infoDate);

            mEventDetails.get(arrayLocation);

            String title = marker.getTitle();
            String date = marker.getSnippet();

            mTitle.setText(title);
            mDate.setText(date);

            return view;

        }

    }

}
