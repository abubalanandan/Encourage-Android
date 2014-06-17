package com.jhl.encourage.activities;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHAlertsAdapter;
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
       // listView = (ListView) findViewById(R.id.alertlist);
        
        List<Notification> alerts = JHAppStateVariables.getNotifications(JHConstants.NOT_TYPE_ALERT);
        Collections.sort(alerts);
//        int size = alerts.size();
//        String[] values = new String[size];
//        
//        for(int i = 0 ; i<size; i++){
//        	Notification alert = alerts.get(i);
//        	values[i] = alert.toString();
//        }
//        
//        Log.d(JHConstants.LOG_TAG, "Care task values "+values);
//        
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//          android.R.layout.simple_list_item_1, android.R.id.text1, values);
//
//
//        // Assign adapter to ListView
//        listView.setAdapter(adapter); 
        JHAlertsAdapter alertAdapter = new JHAlertsAdapter(this, R.layout.alertitem, alerts);
        ListView alertsList = (ListView)findViewById(R.id.alertListView);
        alertsList.setAdapter(alertAdapter);
        
        
    }
    
    
    
    public void closeButtonPressed(View view){
    	finish();
    	try{
		    Intent i = new Intent(JHAlertListActivity.this, JHTimelineActivity.class);
		    startActivity(i);
		    
		    }
		    catch(Exception ex)
		    {
		        Log.e("main",ex.toString());
		    }
    }

}
