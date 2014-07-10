package com.jhl.encourage.activities;


import java.util.Collections;
import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHCareTasksAdapter;
import com.jhl.encourage.model.Notification;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class JHCareTaskListActivity extends Activity {
	ListView listView ;
    
	
	public static int position = 0;
	public static String id;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caretasks);
        listView = (ListView) findViewById(R.id.careTaksList);
        
        List<Notification> careTasks = JHAppStateVariables.getNotifications(JHConstants.NOT_TYPE_CARE_TASK);
        Collections.sort(careTasks);
        JHCareTasksAdapter adapeter = new JHCareTasksAdapter(this,  R.layout.caretask, careTasks);
        
        boolean flag = false;
        int i =0;
        for( i=0;i<careTasks.size();i++){
        	
        	if(careTasks.get(i).getId().equalsIgnoreCase(id)){
        		flag = true;
        		break;
        	}
        }
        listView.setAdapter(adapeter);
        if(flag){
        	listView.setSelection(i);
        }
    }
    
    public void closeButtonClicked(View view) {
		finish();
	}

}
