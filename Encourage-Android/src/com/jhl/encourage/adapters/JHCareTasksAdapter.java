package com.jhl.encourage.adapters;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;
import com.jhl.encourage.apis.MarkCareTaskService;
import com.jhl.encourage.apis.SpocObject;
import com.jhl.encourage.apis.SpocResponse;
import com.jhl.encourage.model.CareTask;
import com.jhl.encourage.model.Notification;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHUtility;
import com.jhl.encourage.views.JHCareTasksDialog;


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
		ImageButton doneButton;
		ImageButton notDoneButton;
		ImageView medTypeImageView;
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
		   holder.title = (TextView) convertView.findViewById(R.id.careTaskTitleTV);
		   holder.due = (TextView) convertView.findViewById(R.id.dueDateAndTimeTV);
		   holder.doneButton = (ImageButton)convertView.findViewById(R.id.btnDone);
		   holder.notDoneButton = (ImageButton)convertView.findViewById(R.id.btnNotDone);	   
		   holder.medTypeImageView = (ImageView)convertView.findViewById(R.id.medTypeImageView);
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
	   
	   holder.due.setText(JHUtility.getFormattedDate(careTask.getDateTime()));
	   holder.title.setText(careTask.getCareplanName());
	   
	   
	   /*if(careTask.getCareTaskType().equalsIgnoreCase("Medication")|| careTask.getCareTaskType().equalsIgnoreCase("Physical Therapy")){
		   holder.medTypeImageView.setBackground(context.getResources().getDrawable(R.drawable.med));
	   }else if(careTask.getCareTaskType().equalsIgnoreCase("Diet Management")){
		   holder.medTypeImageView.setBackground(context.getResources().getDrawable(R.drawable.food));

	   }else if(careTask.getCareTaskType().equalsIgnoreCase("Appointment")){
		   holder.medTypeImageView.setBackground(context.getResources().getDrawable(R.drawable.doc));

	   }*/
	   
	   LinearLayout postLL = (LinearLayout) convertView
				.findViewById(R.id.postLL);
		TableLayout postDetailsTL = (TableLayout)convertView.findViewById(R.id.postDetailsTL);
		//postLL.removeAllViews();
		int count = postLL.getChildCount();
		postLL.removeViews(1, count-1);
		
		
		String details = careTask.getCpDetails();
		details = details.replace('{', ' ');
		details = details.replace('}', ' ');
		details = details.trim();
		String[] elements = details.split(",");
		ArrayList<String[]>detailList = new ArrayList<String[]>();
		for(String element:elements){
			String[] pairs = element.split(":");
			detailList.add(pairs);
		}
		
		Iterator iterator = detailList.iterator();
		
		
		postDetailsTL.removeAllViews();
		while(iterator.hasNext()){
			String[] pairs = (String[])iterator.next();
			if(pairs!=null && pairs.length==2){
				pairs[0] = pairs[0].replaceAll("\"", "");
				pairs[1] = pairs[1].replaceAll("\"", "");
			TableRow.LayoutParams params = new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT);
//			JHTimelineItemView itemView = new JHTimelineItemView(ctx);
//			itemView.setLayoutParams(params);
//			itemView.getKeyTV().setText(pairs.getKey());
//			itemView.getValueTV().setText(pairs.getValue());
//			postLL.addView(itemView);
			TableRow row = new TableRow(context);
			row.setLayoutParams(params);
			TextView keyTV = new TextView(context);
			TextView valueTV = new TextView(context);
			row.addView(keyTV);
			row.addView(valueTV);
			keyTV.setText(pairs[0]);
			valueTV.setText(pairs[1]);
			
			postDetailsTL.addView(row);
			}
			
		}
	   
	   
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
		
		JHUtility.showProgressDialog("Updating caretask status...",(Activity) context);
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
									JHUtility.dismissProgressDialog((Activity)context);
									JHAppStateVariables.markAsRead(JHConstants.NOT_TYPE_CARE_TASK, careTaskId);
									Notification n = new Notification(careTaskId);
									n.setNotificationType(JHConstants.NOT_TYPE_CARE_TASK);
									careTasks.remove(n);
									//JHAppStateVariables.removeCareTask(careTaskId);
									JHCareTasksAdapter.this.notifyDataSetChanged();
								}else{
									JHUtility.dismissProgressDialog((Activity)context);

								}
								
							}
						}
					}

					@Override
					public void failure(RetrofitError retrofitError) {
						System.out.println("error");
						JHUtility.dismissProgressDialog((Activity)context);

					}
				});
		

	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return careTasks.size();
	}
	
}
