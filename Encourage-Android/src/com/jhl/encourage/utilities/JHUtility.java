package com.jhl.encourage.utilities;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;


public class JHUtility {
	
	
	
	public static void showDialogOk(String title, String message, Activity activity){
		new AlertDialog.Builder(activity).setTitle(title).setMessage(message).setPositiveButton("Ok", null).setCancelable(false).create().show();
	}
	
	public static String getTimeZoneString(){
		TimeZone tz = TimeZone.getDefault();		
		Log.d(JHConstants.LOG_TAG, "timezone "+tz.getID());
		return tz.getID();
	}
	
	public static String getDateTime(){
//		java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
//		String date = dateFormat.format(new Date());
		
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = s.format(new Date());
		
		Log.d(JHConstants.LOG_TAG, "datetime "+date);
		return date;
	}

}
