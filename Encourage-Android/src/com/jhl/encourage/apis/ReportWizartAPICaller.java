package com.jhl.encourage.apis;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.util.Log;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.activities.JHReportWizardActivity;
import com.jhl.encourage.activities.JHTimelineActivity;
import com.jhl.encourage.model.Contact;
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
	
	public void invokeSickenssEmotionalApi(String date, String data, String description, boolean ics, Contact contact) {

		RestAdapter restAdapter = EncourageApplication.getRestAdapter();

		SickEmotionReportService service = restAdapter.create(SickEmotionReportService.class);

		String icsString = "no";
		if(ics){
			icsString = "yes";
		}
		String addToCC1 = "no";
		if(contact.isAddToCC1()){
			addToCC1 = "yes";
		}
		String addToCC2 = "no";
		if(contact.isAddToCC1()){
			addToCC2 = "yes";
		}
		
		service.postReport("getSelfReportedData", date, "datetime", "Date", "","1", "", data, "varchar", "Complaint", "", "2","", "", "text", 
				"Description", "", "3", "", contact.getName1(), contact.getEmail1(), contact.getName2(), contact.getEmail2(), "self_reported_form", "selfreport_data" , icsString, 
				JHUtility.getDateTime(), JHUtility.getTimeZoneString(), JHAppStateVariables.getLoginTocken() , "postReportWizardDataa", addToCC1, addToCC2, 
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
	
	public void invokeMapApi(String date, String name, String address, String description, boolean ics, Contact contact) {

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
	
	
	public void invokeImageApi(String date, String name, boolean ics, String fileName) {

		RestAdapter restAdapter = EncourageApplication.getRestAdapter();

		ImageReportService service = restAdapter.create(ImageReportService.class);

		String icsString = "no";
		if(ics){
			icsString = "yes";
		}
		
		String upload_files = "{\"blob_upload_file\":{\"dtl_file_actualname\":{\"dtl_file_actualname\":\"" + fileName + "\",\"data_type\":\"documentactualname\"},\"dtl_file_name\":{\"dtl_file_name\":\"download\",\"data_type\":\"filename\"},\"dtl_file_type\":{\"dtl_file_type\":\"image\\/jpg\",\"data_type\":\"documenttype\"},\"text_file_description\":{\"data_type\":\"description\"},\"dtl_file_author\":{\"data_type\":\"authorname\"},\"dtl_file_category\":{\"data_type\":\"category\"},\"blob_upload_file\":\"" + fileName + "\"}}";
		
		service.postReport("getSelfReportedImage", date, "datetime", "Date", "","1", "", name, "varchar", "Event Name", "", "2","", 
				"timeline-image-form", "selfreport_image", "blob_upload_image",icsString,JHUtility.getDateTime(),
				JHUtility.getTimeZoneString(), JHAppStateVariables.getLoginTocken(), "postReportWizardData", upload_files, 
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
