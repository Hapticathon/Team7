package com.hapticon.t_habits.controllers;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import com.hapticon.t_habits.helpers.CurrentApplicationPackageRetriever;

public class AppController {

	CurrentApplicationPackageRetriever pr = null;
	Context ctx;
	
	public AppController(Context baseContext) {
		ctx = baseContext;
		pr = new CurrentApplicationPackageRetriever(baseContext);
	}
	
	public String getCurrentApp() {
		String[] strings = pr.get();
		if (strings != null && strings.length > 0) {
			return pr.get()[0];
		} else {
			return "";
		}
		
	}

	public void startActivityEventRetriver() {
		retriveCurrentApp();
	}

	private void retriveCurrentApp() {
		Handler h = new Handler();
 		h.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				currentAppRetrived(getCurrentApp());
				retriveCurrentApp();
			}

			
		}, 1000);	
	}

	private void currentAppRetrived(String currentApp) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
		Editor edit = sharedPref.edit();
		int count = sharedPref.getInt(currentApp, 0);
		count++;
		edit.putInt(currentApp, count);
		edit.commit();
		Log.d("THabits", currentApp + " " + count);
	}
	
}
