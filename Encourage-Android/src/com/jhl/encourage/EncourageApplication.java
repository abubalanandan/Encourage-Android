package com.jhl.encourage;


import android.app.Application;

public class EncourageApplication extends Application {

	private static EncourageApplication application;
	
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
	}

	
	public static synchronized EncourageApplication getSharedApplication(){
		return application;
	}
	
	
}
