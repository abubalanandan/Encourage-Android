package com.jhl.encourage;


import retrofit.RestAdapter;
import android.app.Application;

import com.jhl.encourage.apis.SimpleXMLConverter;
import com.jhl.encourage.model.JHUser;
import retrofit.android.MainThreadExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EncourageApplication extends Application {

	private static EncourageApplication application;
	private JHUser currentUser;
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
