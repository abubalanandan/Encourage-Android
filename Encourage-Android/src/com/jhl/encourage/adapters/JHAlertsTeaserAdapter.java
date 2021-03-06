package com.jhl.encourage.adapters;

import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.model.Alert;
import com.jhl.encourage.model.Notification;

import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHUtility;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;


public class JHAlertsTeaserAdapter extends ArrayAdapter<Notification> {
	private List<Notification> alerts;
	private Context context;
	
	public JHAlertsTeaserAdapter(Context context, int textViewResourceId, List<Notification> alerts) {
		   super(context, textViewResourceId, alerts);
		   this.context = context;
		   this.alerts = alerts;
		   Log.d(JHConstants.LOG_TAG, "this.alerts "+this.alerts);
		  
	}
	
	private class ViewHolder {
		TextView title;
		TextView date;
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
		   convertView = vi.inflate(R.layout.alertteaseritem, null);
	 
		   holder = new ViewHolder();
		   holder.title = (TextView) convertView.findViewById(R.id.alertTeaserTitle);
		   holder.date = (TextView) convertView.findViewById(R.id.alertTeaserDate);
		   convertView.setTag(holder);
	 
		  
	   }
	   else {
		   holder = (ViewHolder) convertView.getTag();
	   }
	 
	   Log.d(JHConstants.LOG_TAG, "position "+position);
	   
	   Alert alert = (Alert)alerts.get(position);
	
	   Log.d(JHConstants.LOG_TAG, "alert.getAuthorName() "+alert.getAuthorName());
	   Log.d(JHConstants.LOG_TAG, "alert.getTitle() "+alert.getTitle());
	   
	   holder.date.setText(JHUtility.getFormattedDate(alert.getDateTime()));
	   holder.title.setText(alert.getTitle());
	   holder.date.setTag(JHUtility.getFormattedDate(alert.getDateTime()));
	   holder.title.setTag(alert.getTitle());
	 
	   return convertView;
	 
	  }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alerts.size();
	}
	
}
