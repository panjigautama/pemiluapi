package com.hackathon.pemilu;

import org.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.view.Menu;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
