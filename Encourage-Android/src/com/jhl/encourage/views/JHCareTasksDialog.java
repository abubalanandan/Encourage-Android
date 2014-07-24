package com.jhl.encourage.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.jhl.encourage.R;
import com.jhl.encourage.activities.JHAlertListActivity;
import com.jhl.encourage.activities.JHCareTaskListActivity;
import com.jhl.encourage.adapters.JHCareTasksAdapter;
import com.jhl.encourage.adapters.JHCareTasksTeaserAdapter;
import com.jhl.encourage.model.Notification;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHUtility;
import com.jhl.encourage.views.JHAlertsTeaserDialog.ViewAllClickListener;


public class JHCareTasksDialog extends Dialog {

	//private JHCareTasksAdapter careTasksAdapter;
	private JHCareTasksTeaserAdapter careTasksAdapter;
	private List<Notification> careTasks;
	private final Context context;
	public JHCareTasksDialog(Context context) {
		super(context, R.style.ThemeDialogCustom);
		this.context = context;
		setContentView(R.layout.caretasksteaser);
		ListView listView = (ListView)findViewById(R.id.careTaksTeaserList);		
		careTasks = new ArrayList<Notification>(JHAppStateVariables.getUnreadNotifications(JHConstants.NOT_TYPE_CARE_TASK));		
		Collections.sort(this.careTasks);
		//careTasksAdapter = new JHCareTasksTeaserAdapter(context, this, R.layout.caretask, careTasks);
		careTasksAdapter = new JHCareTasksTeaserAdapter(context, this, R.layout.caretaskteaseritem, careTasks);
		listView.setAdapter(careTasksAdapter);
		
		listView.setOnItemClickListener(new CTClickListener());
		
		RelativeLayout close = (RelativeLayout)findViewById(R.id.closeButton);
		close.setOnClickListener(new Closer());
		
		Button viewAll = (Button)findViewById(R.id.viewAllCareTasks);
		viewAll.setOnClickListener(new ViewAllClickListener());
	}
	
	class Closer implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			cancel();
		}
	}
	
	
	class CTClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Notification n = careTasks.get(position);
			
			System.out.println("OnItemClickListener");
			
			JHCareTaskListActivity.position = position;
			JHCareTaskListActivity.id = n.getId();
			try{
			Intent i = new Intent(context, JHCareTaskListActivity.class);
		    context.startActivity(i);
		    cancel();
		    }
		    catch(Exception ex)
		    {
		        Log.e("main",ex.toString());
		    }	
			
			
		}
	}
	
	
	class ViewAllClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			try{
			    Intent i = new Intent(context, JHCareTaskListActivity.class);
			    context.startActivity(i);
			    cancel();
			    }
			    catch(Exception ex)
			    {
			        Log.e("main",ex.toString());
			    }
			
		}
	}
}
