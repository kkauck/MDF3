//Kyle Kauck

package com.example.kyle.gamewidget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DetailActivity extends Activity {

    public static final String EXTRA_ITEM = "com.example.kyle.MainActivity.EXTRA_ITEM";
    DataHelper mGamesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null){

            DetailFrag frag = DetailFrag.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.detail_activity, frag, DetailFrag.TAG).commit();

            Intent intent = getIntent();
            mGamesHelper = (DataHelper) intent.getSerializableExtra(EXTRA_ITEM);

        } else {

            Intent intent = getIntent();
            mGamesHelper = (DataHelper) intent.getSerializableExtra(EXTRA_ITEM);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#31698A")));

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() ==  android.R.id.home){

            finish();

        }

        return false;

    }

    public DataHelper setValue(){

        return mGamesHelper;

    }

    //Will finish the activity when the home button is pressed as well
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
