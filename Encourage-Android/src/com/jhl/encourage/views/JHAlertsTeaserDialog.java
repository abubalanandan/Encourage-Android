package com.jhl.encourage.views;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;
import com.jhl.encourage.activities.JHAlertListActivity;
import com.jhl.encourage.activities.JHReportWizardActivity;
import com.jhl.encourage.activities.JHTimelineActivity;
import com.jhl.encourage.adapters.JHAlertsTeaserAdapter;
import com.jhl.encourage.apis.MarkAlertReadService;
import com.jhl.encourage.apis.SpocObject;
import com.jhl.encourage.apis.SpocResponse;
import com.jhl.encourage.model.Notification;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHUtility;

public class JHAlertsTeaserDialog extends Dialog {

	private JHAlertsTeaserAdapter teaserAdapter;
	private final Context context;
	private final JHTimelineActivity acivity;
	private List<Notification> alerts;
	public JHAlertsTeaserDialog(Context context) {
		super(context, R.style.ThemeDialogCustom);
		
		setContentView(R.layout.alertsteasers);
		this.context = context;
		this.acivity = (JHTimelineActivity)context;
		ListView listView = (ListView)findViewById(R.id.alertTeaserList);
		
		alerts = JHAppStateVariables.getUnreadNotifications(JHConstants.NOT_TYPE_ALERT);
		
		Collections.sort(this.alerts);
		
		teaserAdapter = new JHAlertsTeaserAdapter(context, R.layout.alertteaseritem, alerts);
		listView.setAdapter(teaserAdapter);
		listView.setOnItemClickListener(new ListItemClickListener());
		
		Button viewAll = (Button)findViewById(R.id.viewAllAlerts);
		viewAll.setOnClickListener(new ViewAllClickListener());
		
		
	}
	
	class ViewAllClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			try{
			    Intent i = new Intent(context, JHAlertListActivity.class);
			    context.startActivity(i);
			    cancel();
			    }
			    catch(Exception ex)
			    {
			        Log.e("main",ex.toString());
			    }
			
		}
	}
	
	class ListItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Notification n = alerts.get(position);
			JHAppStateVariables.markAsRead(JHConstants.NOT_TYPE_ALERT, n.getId());
			
			try{
			    Intent i = new Intent(context, JHAlertListActivity.class);
			    context.startActivity(i);
			    cancel();
			    }
			    catch(Exception ex)
			    {
			        Log.e("main",ex.toString());
			    }
			
		}
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
									Intent intent = new Intent(context, JHTimelineActivity.class);
									context.startActivity(intent);
									cancel();
								}else{
									System.out.println("error");
									JHUtility.showDialogOk("",context.getString(R.string.alert_updation_failed), acivity);	
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
