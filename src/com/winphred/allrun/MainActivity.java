package com.winphred.allrun;

import com.winphred.allrun.activity.RegisterActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
 
public class MainActivity extends Activity {
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					try {
						startActivity(new Intent(MainActivity.this,
								RegisterActivity.class));
						Log.i("Start APP", "Hello");
					} catch (Exception e) {
						e.printStackTrace();

					}

				}
			}, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
