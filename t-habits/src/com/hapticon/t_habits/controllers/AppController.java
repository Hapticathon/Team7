package com.hapticon.t_habits.controllers;

import android.content.Context;

import com.hapticon.t_habits.helpers.CurrentApplicationPackageRetriever;

public class AppController {

	CurrentApplicationPackageRetriever pr = null;
	Context ctx;
	
	public AppController(Context baseContext) {
		ctx = baseContext;
		pr = new CurrentApplicationPackageRetriever(baseContext);
	}
	
	public String getCurrentApp() {
		return pr.get()[0];
	}

}
