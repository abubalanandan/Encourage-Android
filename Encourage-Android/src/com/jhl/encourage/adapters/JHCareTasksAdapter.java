package com.jhl.encourage.adapters;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;
import com.jhl.encourage.activities.JHTimelineActivity;
import com.jhl.encourage.apis.MarkCareTaskService;
import com.jhl.encourage.apis.SpocObject;
import com.jhl.encourage.apis.SpocResponse;
import com.jhl.encourage.model.CareTask;
import com.jhl.encourage.model.Notification;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHUtility;
import com.jhl.encourage.views.JHCareTasksDialog;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


public class JHCareTasksAdapter extends ArrayAdapter<Notification> {
	private List<Notification> careTasks;
	private Context context;
	private JHCareTasksDialog dialog;
	
	public JHCareTasksAdapter(Context context, JHCareTasksDialog dialog, int textViewResourceId, List<Notification> careTasks) {
		   super(context, textViewResourceId, careTasks);
		   this.careTasks = careTasks;
		   this.context = context;
		   this.dialog = dialog;
		   Log.d(JHConstants.LOG_TAG, "this.careTasks "+this.careTasks);
		  
	}
	
	private class ViewHolder {
		TextView title;
		TextView due;
		Button doneButton;
		Button notDoneButton;
	}
	
	public List<Notification>  getCareTasks() {
		return careTasks;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	 
	   ViewHolder holder = null;
	   Log.d(JHConstants.LOG_TAG, String.valueOf(position));
	 
	   if (convertView == null) {
		   LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   convertView = vi.inflate(R.layout.caretask, null);
	 
		   holder = new ViewHolder();
		   holder.title = (TextView) convertView.findViewById(R.id.careTaskTitle);
		   holder.due = (TextView) convertView.findViewById(R.id.careTaskDue);
		   holder.doneButton = (Button)convertView.findViewById(R.id.btnDone);
		   holder.notDoneButton = (Button)convertView.findViewById(R.id.btnNotDone);	   
		   
		   holder.doneButton.setOnClickListener(new DoneButtonClicked(position));
		   holder.notDoneButton.setOnClickListener(new NotDoneButtonClicked(position));
		   
		   convertView.setTag(holder);
	 
		  
	   }
	   else {
		   holder = (ViewHolder) convertView.getTag();
	   }
	 
	   Log.d(JHConstants.LOG_TAG, "position "+position);
	   
	   CareTask careTask = (CareTask) careTasks.get(position);
	
	   Log.d(JHConstants.LOG_TAG, "careTask.getDateTime() "+careTask.getDateTime());
	   Log.d(JHConstants.LOG_TAG, "careTask.getCareplanName() "+careTask.getCareplanName());
	   
	   holder.due.setText(careTask.getDateTime());
	   holder.title.setText(careTask.getCareplanName());
	   holder.due.setTag(careTask.getDateTime());
	   holder.title.setTag(careTask.getCareplanName());
	 
	   return convertView;
	 
	  }
	
	class DoneButtonClicked implements View.OnClickListener {
		private int position;
		public DoneButtonClicked(int position) {
			this.position = position;
		}
		
		@Override
		public void onClick(View v) {
			CareTask ct = (CareTask)careTasks.get(position);
			invokeMarkCareTaskApi(ct.getId(), "D", "", "");
			
		}
	}
	
	class NotDoneButtonClicked implements View.OnClickListener {
		private int position;
		public NotDoneButtonClicked(int position) {
			this.position = position;
		}
		
		@Override
		public void onClick(View v) {
			CareTask ct = (CareTask)careTasks.get(position);
			invokeMarkCareTaskApi(ct.getId(), "ND", "", "");
			
		}
	}
	
	
	public void invokeMarkCareTaskApi(final String careTaskId, String status, String logitude, String latitude) {
		Log.d(JHConstants.LOG_TAG, "careTaskId "+careTaskId);
		RestAdapter restAdapter = EncourageApplication.getRestAdapter();

		MarkCareTaskService service = restAdapter.create(MarkCareTaskService.class);
		
		service.updateCareTask("updateCareTaskStatus",careTaskId, status, JHAppStateVariables.getLoginTocken(), JHUtility.getDateTime(), 
				JHUtility.getTimeZoneString(), logitude, latitude, 
				new Callback<SpocResponse>() {
					@Override
					public void success(SpocResponse spocResponse,	Response response) {
						ArrayList<SpocObject> responseList = spocResponse.getSpocObjects();
						for (SpocObject spocObject : responseList) {
							if (spocObject.getResultTypeCode().equalsIgnoreCase("STATUS")) {
								HashMap<String, String> map = spocObject.getMap();
								String success = map.get("success");
								
								Log.d(JHConstants.LOG_TAG, "invokeMarkCareTaskApi success "+success);
								
								if(success.equalsIgnoreCase("true")){
									JHAppStateVariables.markAsRead(JHConstants.NOT_TYPE_CARE_TASK, careTaskId);
									JHAppStateVariables.removeCareTask(careTaskId);
									dialog.cancel();
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
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return careTasks.size();
	}
	
}
