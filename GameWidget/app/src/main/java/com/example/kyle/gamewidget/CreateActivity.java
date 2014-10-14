package com.example.kyle.gamewidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CreateActivity extends Activity implements CreateFragment.gameDetails{

    public static final String EXTRA_ITEM = "com.example.kyle.CreateActivity.EXTRA_ITEM";
    private int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
    private static final String FILENAME = "games.txt";
    private ArrayList<DataHelper> mGameDetails = new ArrayList<DataHelper>();
    DataHelper mDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null){

            CreateFragment frag = CreateFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.create_activity, frag, CreateFragment.TAG).commit();

            Intent intent = this.getIntent();
            mGameDetails = (ArrayList<DataHelper>) intent.getSerializableExtra(EXTRA_ITEM);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#31698A")));

        return true;

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

    public void saveContact(){

        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_ITEM, mGameDetails);

        try {

            FileOutputStream output = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream stream = new ObjectOutputStream(output);

            for (int i = 0; i < mGameDetails.size(); i++){

                mDataHelper = mGameDetails.get(i);
                stream.writeObject(mDataHelper);

            }

            stream.close();

        } catch (Exception e){

            e.printStackTrace();

        }

        setResult(RESULT_OK, intent);
        finish();

    }


    @Override
    public void details(String _title, String _platform, String _genre) {

        mGameDetails = MainActivity.mGameDetails;

        mGameDetails.add(new DataHelper(_title, _platform, _genre));

    }


}
