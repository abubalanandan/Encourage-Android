package com.jhl.encourage;


import android.app.Application;
import com.jhl.encourage.apis.SimpleXMLConverter;
import retrofit.RestAdapter;

public class EncourageApplication extends Application {

	private static EncourageApplication application;

    private static RestAdapter restAdapter;
	
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
        restAdapter = new RestAdapter.Builder().setEndpoint("https://tryencourage.com/hwdsi/hwservice").setConverter(new SimpleXMLConverter()).build();
	}

	
	public static synchronized EncourageApplication getSharedApplication(){
		return application;
	}

    public static RestAdapter getRestAdapter(){
        return restAdapter;
    }
	
	
}
