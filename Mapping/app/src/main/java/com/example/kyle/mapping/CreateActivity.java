//Kyle Kauck

package com.example.kyle.mapping;

import android.app.Activity;
import android.os.Bundle;

public class CreateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);

        if (savedInstanceState == null){

            CreateFragment frag = new CreateFragment();
            getFragmentManager().beginTransaction().replace(R.id.create_container, frag).commit();

        }

    }
}
