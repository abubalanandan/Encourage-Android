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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class JHAlertListActivity extends Activity {
	ListView listView ;
    
	public static int position = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alerts);
        
        List<Notification> alerts = JHAppStateVariables.getNotifications(JHConstants.NOT_TYPE_ALERT);
        Collections.sort(alerts);

        JHAlertsAdapter alertAdapter = new JHAlertsAdapter(this, R.layout.alertitem, alerts);
        ListView alertsList = (ListView)findViewById(R.id.alertListView);
        alertsList.setAdapter(alertAdapter);
        
        if(position != 0 ){
        	alertsList.smoothScrollToPosition(position+1);
        	alertsList.setSelectionFromTop(position+1, 10);
        	position = 0;
        }
//    	listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				if(alerts.get(position).g().equalsIgnoreCase("Map")|| list.get(position).getDatatype().equalsIgnoreCase("Image")){
//					ImageView imageView = (ImageView)view.findViewWithTag(list.get(position).getTimelineid());
//					if(imageView!=null){
//						if(imageView.getBackground().equals(getResources().getDrawable(R.drawable.retry)))
//						imageLoader.DisplayImage(list.get(position).getFilename(), imageView);
//					}
//				}
//				
//			}
//		});
        
    }
    
    
    
    public void closeButtonPressed(View view){
    	finish();
    	}
}
