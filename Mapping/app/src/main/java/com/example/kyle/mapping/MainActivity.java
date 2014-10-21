package com.example.kyle.mapping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private final int CREATECODE = 101;
    private final String EXTRASTRING = "Event_Details";
    public static ArrayList<DataHelper> mEventDetails = new ArrayList<DataHelper>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
}
