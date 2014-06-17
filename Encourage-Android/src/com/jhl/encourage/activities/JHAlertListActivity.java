package com.jhl.encourage.activities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;
import com.jhl.encourage.apis.LoginService;
import com.jhl.encourage.apis.MarkAlertReadService;
import com.jhl.encourage.apis.SpocObject;
import com.jhl.encourage.apis.SpocResponse;
import com.jhl.encourage.model.Notification;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHUtility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class JHAlertListActivity extends Activity {
	ListView listView ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alerts);
        listView = (ListView) findViewById(R.id.alertlist);
        
        List<Notification> alerts = JHAppStateVariables.getNotifications(JHConstants.NOT_TYPE_ALERT);
        int size = alerts.size();
        String[] values = new String[size];
        
        for(int i = 0 ; i<size; i++){
        	Notification alert = alerts.get(i);
        	values[i] = alert.toString();
        }
        
        Log.d(JHConstants.LOG_TAG, "Care task values "+values);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter); 
        
        
    }
    
    public void invokeMarkAlertReadApi(String token, final String alertkey) {
    	
    	RestAdapter restAdapter = EncourageApplication.getRestAdapter();

    	MarkAlertReadService service = restAdapter.create(MarkAlertReadService.class);

		Log.d(JHConstants.LOG_TAG, "token "+token);
		Log.d(JHConstants.LOG_TAG, "alertkey "+alertkey);
		
		String timeZone = JHUtility.getTimeZoneString();
		String dateTime = JHUtility.getDateTime();
		String longitude = "";
		String latitude = "";
		
		service.updateAlertRead("updateUnreadAlertStatus", token, alertkey,timeZone, dateTime, longitude, latitude, 
				new Callback<SpocResponse>() {
					@Override
					public void success(SpocResponse spocResponse,	Response response) {
						ArrayList<SpocObject> responseList = spocResponse.getSpocObjects();
						for (SpocObject spocObject : responseList) {
							if (spocObject.getResultTypeCode().equalsIgnoreCase("STATUS")) {
								HashMap<String, String> map = spocObject.getMap();
								String success = map.get("success");
								if(success.equalsIgnoreCase("true")){
									System.out.println("success");
									JHAppStateVariables.removeAlert(alertkey);
									Intent intent = new Intent(JHAlertListActivity.this, JHTimelineActivity.class);
									startActivity(intent);
									finish();
								}else{
									System.out.println("error");
									JHUtility.showDialogOk("",getString(R.string.alert_updation_failed), JHAlertListActivity.this);	
								}
								
							}
						}
					}

					@Override
					public void failure(RetrofitError retrofitError) {
						System.out.println("error");
					}
				});
    }

}
