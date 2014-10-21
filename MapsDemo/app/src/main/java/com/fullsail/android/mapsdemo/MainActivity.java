package com.fullsail.android.mapsdemo;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		MyMapFragment frag = new MyMapFragment();
		getFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
	}
}
