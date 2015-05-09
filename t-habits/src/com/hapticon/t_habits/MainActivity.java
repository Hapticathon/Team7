package com.hapticon.t_habits;

import com.hapticon.t_habits.controllers.AppController;
import com.hapticon.t_habits.service.ServiceConnector;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	ServiceConnector connector = null;
	private AppController mController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		connector = new ServiceConnector(this);
		
		mController = new AppController(getBaseContext());
 		print();
	}
	
	public void print() {
		Handler h = new Handler();
 		h.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				Log.d("THabits", mController.getCurrentApp());
				print();
			}
		}, 1000);
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            connector.doUnbindService();
        } catch (Throwable t) {
            Log.e("MainActivity", "Failed to unbind from the service", t);
        }
    }
}
