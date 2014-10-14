package com.example.kyle.gamewidget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends Activity implements MainFragment.gameDetails {

    public static final String EXTRA_ITEM = "com.example.kyle.MainActivity.EXTRA_ITEM";
    private final int CREATECODE = 101;
    public static ArrayList<DataHelper> mGameDetails = new ArrayList<DataHelper>();
    private static final String FILENAME = "games.txt";
    DataHelper mDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mGameDetails.clear();

        if (savedInstanceState == null){

            MainFragment frag =  MainFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.activity_main, frag, MainFragment.TAG).commit();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#31698A")));

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add) {

            createGame();

            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    public void loadData(){

        MainFragment frag = (MainFragment) getFragmentManager().findFragmentById(R.id.activity_main);
        mGameDetails.clear();

        try {

            FileInputStream input = openFileInput(FILENAME);
            ObjectInputStream stream = new ObjectInputStream(input);

            while (input.available() != 0){

                mDataHelper = (DataHelper) stream.readObject();
                mGameDetails.add(mDataHelper);

            }

            stream.close();
            frag.setListAdapter(new CustomListAdapter(getApplicationContext(), mGameDetails));

        } catch (Exception e){

            e.printStackTrace();

        }

    }

    public void updateData(){

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

    }

    private void createGame(){

        Intent createGame = new Intent (this, CreateActivity.class);
        createGame.putExtra(CreateActivity.EXTRA_ITEM, mGameDetails);
        startActivityForResult(createGame, CREATECODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if  (requestCode ==  CREATECODE && resultCode == RESULT_OK){

            mGameDetails = (ArrayList<DataHelper>) data.getSerializableExtra(EXTRA_ITEM);
            MainFragment frag = (MainFragment) getFragmentManager().findFragmentById(R.id.activity_main);
            frag.setListAdapter(new CustomListAdapter(this, mGameDetails));

        }

    }

    public ArrayList<DataHelper> getArray(){

        return mGameDetails;

    }

    @Override
    public void returnNewArray(ArrayList<DataHelper> gameArray){

        mGameDetails = gameArray;

    }

}
