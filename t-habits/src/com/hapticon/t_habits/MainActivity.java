package com.hapticon.t_habits;

import com.hapticon.t_habits.R;
import com.hapticon.t_habits.controllers.AppController;
import com.hapticon.t_habits.service.ServiceConnector;

import nxr.tpad.lib.TPad;
import nxr.tpad.lib.TPadImpl;
import nxr.tpad.lib.views.DepthMapView;
import nxr.tpad.lib.views.FrictionMapView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hapticon.t_habits.service.ServiceConnector;

public class MainActivity extends Activity {
	
	// Custom Haptic Rendering view defined in TPadLib
	FrictionMapView fricView;
	
	// TPad object defined in TPadLib
	TPad mTpad;
	
	ServiceConnector connector = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Load new tpad object from TPad Implementation Library
		mTpad = new TPadImpl(this);
		
		// Link friction view to .xml file
		fricView = (FrictionMapView) findViewById(R.id.view1);
		
		// Link local tpad object to the FrictionMapView
		fricView.setTpad(mTpad);
		
		// Load in the image stored in the drawables folder
		Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.filter);
		
		// Set the friction data bitmap to the test image
		fricView.setDataBitmap(defaultBitmap);
		defaultBitmap.recycle();
		
		fricView.setDisplayShowing(false);
		
		connector = new ServiceConnector(this);
		
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
		mTpad.disconnectTPad();
		
        super.onDestroy();
        try {
            connector.doUnbindService();
        } catch (Throwable t) {
            Log.e("MainActivity", "Failed to unbind from the service", t);
        }
    }
}
