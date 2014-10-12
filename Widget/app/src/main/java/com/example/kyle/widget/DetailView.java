//Kyle Kauck

package com.example.kyle.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kyle.widget.Data_and_Adapters.DataHelper;
import com.example.kyle.widget.Fragments.DetailFragment;

public class DetailView extends Activity {

    private DataHelper mGameDetails = new DataHelper();
    DataHelper mDataHelper;
    private static final String FILENAME = "games.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null){

            DetailFragment frag = DetailFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.detail_main, frag, DetailFragment.TAG).commit();

            Intent intent = this.getIntent();

            mDataHelper = (DataHelper) intent.getSerializableExtra("game_details");

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

        return mDataHelper;

    }
}
