package com.jhl.encourage.activities;



import java.util.Collections;
import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHAlertsAdapter;
import com.jhl.encourage.model.Notification;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class JHAlertListActivity extends Activity {
	ListView listView ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alerts);
        
        List<Notification> alerts = JHAppStateVariables.getNotifications(JHConstants.NOT_TYPE_ALERT);
        Collections.sort(alerts);

        JHAlertsAdapter alertAdapter = new JHAlertsAdapter(this, R.layout.alertitem, alerts);
        ListView alertsList = (ListView)findViewById(R.id.alertListView);
        alertsList.setAdapter(alertAdapter);
        
        
    }
    
    
    
    public void closeButtonPressed(View view){
    	finish();
    	}
}
