package com.example.kyle.servicefundamentals;

import android.app.Activity;
import android.os.Bundle;

import com.example.kyle.servicefundamentals.Fragments.UIFragment;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){

            UIFragment frag = UIFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.main_container, frag, UIFragment.TAG).commit();

        }

    }

}
