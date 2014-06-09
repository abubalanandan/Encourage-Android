package com.jhl.encourage.activities;


import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.model.Notification;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

}
