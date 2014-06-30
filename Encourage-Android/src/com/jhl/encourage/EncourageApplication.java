package com.jhl.encourage;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;
import android.app.Application;
import android.text.Html;
import android.util.Log;

import com.jhl.encourage.apis.SimpleXMLConverter;
import com.jhl.encourage.imageloader.JHImageLoader;
import com.jhl.encourage.model.JHUser;

public class EncourageApplication extends Application {

	private static EncourageApplication application;
	private JHUser currentUser;
	private JHImageLoader imageLoader;
	public JHImageLoader getImageLoader() {
		return imageLoader;
	}


	public void setImageLoader(JHImageLoader imageLoader) {
		this.imageLoader = imageLoader;
	}


	public JHUser getCurrentUser() {
		return currentUser;
	}


	public void setCurrentUser(JHUser currentUser) {
		this.currentUser = currentUser;
	}

	private static RestAdapter restAdapter;
	
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
        Executor executor = Executors.newFixedThreadPool(5);
        restAdapter = new RestAdapter.Builder().setEndpoint("https://tryencourage.com/hwdsi")
                .setConverter(new SimpleXMLConverter())
                .setExecutors(executor, new MainThreadExecutor()).build();
        
	}

	
	public static synchronized EncourageApplication getSharedApplication(){
		return application;
	}

    public static RestAdapter getRestAdapter(){
        return restAdapter;
    }
	
	
}
