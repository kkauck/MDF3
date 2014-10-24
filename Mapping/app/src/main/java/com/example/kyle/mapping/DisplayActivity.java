//Kyle Kauck

package com.example.kyle.mapping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class DisplayActivity extends Activity {

    private final String EXTRASTRING = "Event_Details";
    DataHelper mDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {

            DisplayFragment frag = new DisplayFragment();
            getFragmentManager().beginTransaction().replace(R.id.display_container, frag).commit();

            Intent intent = getIntent();
            mDataHelper = (DataHelper) intent.getSerializableExtra(EXTRASTRING);

            Log.i("Hi", "Bye");

        }

    }

    public DataHelper setValue(){

        return mDataHelper;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() ==  android.R.id.home){

            finish();

        }

        return false;

    }

    @Override
    protected void onStop() {

        super.onStop();
        finish();

    }
}
