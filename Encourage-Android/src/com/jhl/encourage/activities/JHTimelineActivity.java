package com.jhl.encourage.activities;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHTimelineAdapter;
import com.jhl.encourage.apis.SpocObject;
import com.jhl.encourage.apis.SpocResponse;
import com.jhl.encourage.apis.TimeLineService;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.views.JHAlertsTeaserDialog;
import com.jhl.encourage.views.JHCareTasksDialog;

public class JHTimelineActivity extends Activity {

	private ListView timelineView;
	private JHTimelineAdapter adapter;
	private ArrayList<String> list = new ArrayList<>(3);
	private int lastCount=0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
//		for (int i = 0; i < 9; i++) {
//			list.add("lala");
//		}
		String getMapURL = "http://maps.googleapis.com/maps/api/staticmap?zoom=14&size=1080x1920&markers=size:mid|color:red|rockville"  
				
				+ "&sensor=false";
		list.add(getMapURL);
		list.add("http://c1038.r38.cf3.rackcdn.com/group1/building7332/media/awnw_bmw_headquarters_building.jpg");
		list.add("https://tryencourage.com/hwdsi/hwAttachedfile/2b289cc910c293ab042e295d7177c7df/705C9D80-EF12-7DD6-EF66-86F395FD6708.10%20pm");
		list.add("");
		list.add("http://image.eurotuner.com/f/featuredvehicles/8307328/eurp_0802_16_z%2B1975_bmw_530i%2Btop_view.jpg");
		initViews();
		invokeTimelineDetailsApi("", "2014-06-22 01:07:18", "Asia/Kolkata");
		}

//	private void initViews() {
//		timelineView = (ListView) findViewById(R.id.timeLineView);
//
//		ProgressBar dialog = new ProgressBar(this);
//		dialog.setProgressDrawable(getResources().getDrawable(
//				android.R.drawable.progress_indeterminate_horizontal));
//		timelineView.addFooterView(dialog);
//		adapter = new JHTimelineAdapter(this, list, false);
//		timelineView.setAdapter(adapter);
//		adapter.notifyDataSetChanged();
//		
//		RelativeLayout alertsButton = (RelativeLayout)findViewById(R.id.alertButton);
//		alertsButton.setOnClickListener(new AlertClicked());
//		
//		RelativeLayout ctButton = (RelativeLayout)findViewById(R.id.careTaskButton);
//		ctButton.setOnClickListener(new CTClicked());
//	}

		
	class AlertClicked implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			if(JHAppStateVariables.getUnreadNotificationCount(JHConstants.NOT_TYPE_ALERT) > 0 ) { 
				JHAlertsTeaserDialog dialog = new JHAlertsTeaserDialog(JHTimelineActivity.this);
				dialog.show();
			}
		}
	}
	
	class CTClicked implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			if(JHAppStateVariables.getUnreadNotificationCount(JHConstants.NOT_TYPE_CARE_TASK) > 0 ) { 
				JHCareTasksDialog dialog = new JHCareTasksDialog(JHTimelineActivity.this);
				dialog.show();
			}
			
		}
	}


	
	

	private void initViews() {
		
		timelineView = (ListView) findViewById(R.id.timeLineView);

		ProgressBar dialog = new ProgressBar(this);
		dialog.setProgressDrawable(getResources().getDrawable(
				android.R.drawable.progress_indeterminate_horizontal));
		timelineView.addFooterView(dialog);
		adapter = new JHTimelineAdapter(this, list, false);
		timelineView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		RelativeLayout alertsButton = (RelativeLayout)findViewById(R.id.alertButton);
		alertsButton.setOnClickListener(new AlertClicked());
		
		RelativeLayout ctButton = (RelativeLayout)findViewById(R.id.careTaskButton);
		ctButton.setOnClickListener(new CTClicked());
		ImageButton reportButton = (ImageButton) findViewById(R.id.reportButton);
		reportButton.setOnClickListener(new ReportClicked());
		
		TextView alertNumberView = (TextView) findViewById(R.id.alertnumberView);
		alertNumberView.setText(JHAppStateVariables.getUnreadNotificationCount(JHConstants.NOT_TYPE_ALERT)+"");
		
		JHAppStateVariables.alertNumberView = alertNumberView;
		
		TextView careTaskNumberView = (TextView) findViewById(R.id.caretasknumberView);
		careTaskNumberView.setText(JHAppStateVariables.getUnreadNotificationCount(JHConstants.NOT_TYPE_CARE_TASK)+"");
		
		JHAppStateVariables.careTaskNumberView = careTaskNumberView;
		
		JHAppStateVariables.timeLineActivity = this;
		
		
	}
	
	

	private void invokeTimelineDetailsApi(String careTargetId, String dateTime,
			String timeZone) {

		TimeLineService service = EncourageApplication.getRestAdapter().create(
				TimeLineService.class);
		String token = JHAppStateVariables.getLoginTocken();
		
		service.getTimeLineDetails("getTimelineDetails", token, careTargetId,
				dateTime, timeZone, new Callback<SpocResponse>() {
					@Override
					public void success(SpocResponse spocResponse,
							Response response) {

						ArrayList<SpocObject> responseList = spocResponse
								.getSpocObjects();
						for (SpocObject spocObject : responseList) {
							if (spocObject.getResultTypeCode()
									.equalsIgnoreCase("STATUS")) {
								HashMap<String, String> map = spocObject
										.getMap();
								String success = map.get("success");
								if (success.equalsIgnoreCase("true")) {
									System.out.println("success");

								} else {
									System.out.println("error");

								}

							}else if (spocObject.getResultTypeCode().equalsIgnoreCase("READONLY_FORM")){
								HashMap<String, String> map = spocObject.getMap();
								if(map.containsKey("lastcount")){
									lastCount =Integer.parseInt(map.get("lastcount"));
								}else{
									
									
								}
							}
						}

					}

					@Override
					public void failure(RetrofitError retrofitError) {

						System.out.println("Retro error");

					}
				});

	}
	
	
	class ReportClicked implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			try{
			    Intent i = new Intent(JHTimelineActivity.this, JHReportWizardActivity.class);
			    startActivity(i);
			    }
			    catch(Exception ex)
			    {
			        Log.e("main",ex.toString());
			    }
		}
	}
	
	@Override
	public void onBackPressed(){
		//Disabled back button action
	}
	

	
}
