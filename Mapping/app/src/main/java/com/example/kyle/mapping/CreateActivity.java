//Kyle Kauck

package com.example.kyle.mapping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CreateActivity extends Activity implements CreateFragment.eventDetails {

    private final String EXTRASTRING = "Event_Details";
    private static final String FILENAME = "events.text";
    private ArrayList<DataHelper> mEventDetails = new ArrayList<DataHelper>();
    DataHelper mDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState == null){

            CreateFragment frag = new CreateFragment();
            getFragmentManager().beginTransaction().replace(R.id.create_container, frag).commit();

            Intent intent = this.getIntent();
            mEventDetails = (ArrayList<DataHelper>) intent.getSerializableExtra(EXTRASTRING);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){

            saveContact();

        }

        return false;

    }

    @Override
    public void onBackPressed() {

        saveContact();

    }

    @Override
    public void details(String _name, String _time, String _image, double _lat, double _long) {

        mEventDetails = MainActivity.mEventDetails;

        mEventDetails.add(new DataHelper(_name, _time, _image, _lat, _long));

        Log.i("Hi", "Bye");

    }

    public void saveContact(){

        Intent intent = new Intent();
        intent.putExtra(EXTRASTRING, mEventDetails);

        try {

            FileOutputStream output = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream stream = new ObjectOutputStream(output);

            for (int i = 0; i < mEventDetails.size(); i++){

                mDataHelper = mEventDetails.get(i);
                stream.writeObject(mDataHelper);

            }

            stream.close();

        } catch (Exception e){

            e.printStackTrace();

        }

        setResult(RESULT_OK, intent);
        finish();

    }

}
