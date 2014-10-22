package com.example.kyle.mapping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private final int CREATECODE = 101;
    private final String EXTRASTRING = "Event_Details";
    private static final String FILENAME = "events.text";
    public static ArrayList<DataHelper> mEventDetails = new ArrayList<DataHelper>();
    DataHelper mDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        DisplayMap frag = new DisplayMap();
        getFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_create) {

            createNewData();

            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void createNewData(){

        Intent create = new Intent(this, CreateActivity.class);
        create.putExtra(EXTRASTRING, mEventDetails);
        startActivityForResult(create, CREATECODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATECODE && resultCode == RESULT_OK){

            mEventDetails = (ArrayList<DataHelper>) data.getSerializableExtra(EXTRASTRING);

            Log.i("Hi", "Bye");


        }

    }

    public void loadData(){

        mEventDetails.clear();

        try {

            FileInputStream input = openFileInput(FILENAME);
            ObjectInputStream stream = new ObjectInputStream(input);

            while (input.available() != 0){

                mDataHelper = (DataHelper) stream.readObject();
                mEventDetails.add(mDataHelper);

            }

            stream.close();

        } catch (Exception e){

            e.printStackTrace();

        }

    }

}
