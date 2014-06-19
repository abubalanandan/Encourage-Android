package com.jhl.encourage.apis;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.util.Log;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.activities.JHReportWizardActivity;
import com.jhl.encourage.activities.JHTimelineActivity;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHUtility;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ReportWizartAPICaller {
	
	private JHReportWizardActivity activity;
	
	public ReportWizartAPICaller(JHReportWizardActivity activity) {
		this.activity = activity;
	}
	
	public void invokeSickenssEmotionalApi(String date, String data, String description, boolean ics) {

		RestAdapter restAdapter = EncourageApplication.getRestAdapter();

		SickEmotionReportService service = restAdapter.create(SickEmotionReportService.class);

		String icsString = "no";
		if(ics){
			icsString = "yes";
		}
		
		service.postReport("getSelfReportedData", date, "datetime", "Date", "","1", "", data, "varchar", "Complaint", "", "2","", "", "text", 
				"Description", "", "3", "", "", "", "", "", "self_reported_form", "selfreport_data" , icsString, 
				JHUtility.getDateTime(), JHUtility.getTimeZoneString(), JHAppStateVariables.getLoginTocken() , "postReportWizardDataa", "no", "no", 
				new Callback<SpocResponse>() {
					@Override
					public void success(SpocResponse spocResponse,	Response response) {
						ArrayList<SpocObject> responseList = spocResponse.getSpocObjects();
						for (SpocObject spocObject : responseList) {
							if (spocObject.getResultTypeCode().equalsIgnoreCase("STATUS")) {
								HashMap<String, String> map = spocObject.getMap();
								String success = map.get("success");
								
								Log.d(JHConstants.LOG_TAG, "postReport success "+success);
								
								if(success.equalsIgnoreCase("true")){
									Intent intent = new Intent(activity, JHTimelineActivity.class);
									activity.startActivity(intent);
									activity.finish();
								}else{
									
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
	
	public void invokeMapApi(String date, String name, String address, String description, boolean ics) {

		RestAdapter restAdapter = EncourageApplication.getRestAdapter();

		MapReportService service = restAdapter.create(MapReportService.class);

		String icsString = "no";
		if(ics){
			icsString = "yes";
		}
		
		service.postReport("getSelfReportedMap", date, "datetime", "Date", "","1", "", name, "varchar", "Event Name", "", "2","", address, "text", "Event Address", "", "3", "",  description, "text", "Description", "", "4", "","", "", "", "","timeline-map-form", "selfreport_map", icsString, 
		JHUtility.getDateTime(), JHUtility.getTimeZoneString(), JHAppStateVariables.getLoginTocken() , "postReportWizardData", "no", "no", 
				new Callback<SpocResponse>() {
					@Override
					public void success(SpocResponse spocResponse,	Response response) {
						ArrayList<SpocObject> responseList = spocResponse.getSpocObjects();
						for (SpocObject spocObject : responseList) {
							if (spocObject.getResultTypeCode().equalsIgnoreCase("STATUS")) {
								HashMap<String, String> map = spocObject.getMap();
								String success = map.get("success");
								
								Log.d(JHConstants.LOG_TAG, "postReport success "+success);
								
								if(success.equalsIgnoreCase("true")){
									Intent intent = new Intent(activity, JHTimelineActivity.class);
									activity.startActivity(intent);
									activity.finish();
								}else{
									
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
