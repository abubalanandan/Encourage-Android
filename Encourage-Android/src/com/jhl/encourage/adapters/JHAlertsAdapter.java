package com.jhl.encourage.adapters;

import java.util.Collections;
import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.model.Notification;

import com.jhl.encourage.utilities.JHConstants;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class JHAlertsAdapter extends ArrayAdapter<Notification> {
	private List<Notification> alerts;
	private Context context;
	
	public JHAlertsAdapter(Context context, int textViewResourceId, List<Notification> alerts) {
		   super(context, textViewResourceId, alerts);
		   this.context = context;
		   this.alerts = alerts;
		 
		   Collections.sort(this.alerts);
	}
	
	private class ViewHolder {
		TextView author;
		TextView title;
		TextView details;
		TextView dateTime;
	}
	
	public List<Notification>  getAlerts() {
		return alerts;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	 
	   ViewHolder holder = null;
	   Log.d(JHConstants.LOG_TAG, String.valueOf(position));
	 
	   if (convertView == null) {
		   LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   convertView = vi.inflate(R.layout.alertitem, null);
	 
		   holder = new ViewHolder();
		   holder.title = (TextView) convertView.findViewById(R.id.alertTitle);
		   holder.author = (TextView) convertView.findViewById(R.id.alertAuthor);
		   holder.details = (TextView) convertView.findViewById(R.id.alertDetails);
		   holder.dateTime = (TextView) convertView.findViewById(R.id.alertDateTime);
		   convertView.setTag(holder);
	 
		  
	   }
	   else {
		   holder = (ViewHolder) convertView.getTag();
	   }
	 
	 
	   Notification notification = alerts.get(position);
	
	 
	   holder.author.setText(notification.getAuthorName());
	   holder.title.setText(notification.getTitle());
	   holder.details.setText(notification.getDetails());
	   holder.dateTime.setText(notification.getDateTime());
	   
//	   holder.author.setTag(notification.getAuthorName());
//	   holder.title.setTag(notification.getTitle());
//	 
	   return convertView;
	 
	  }
	
}
