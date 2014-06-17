package com.jhl.encourage.utilities;

import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;

import com.jhl.encourage.activities.JHTimelineActivity;
import com.jhl.encourage.model.Alert;
import com.jhl.encourage.model.CareTask;
import com.jhl.encourage.model.Contact;
import com.jhl.encourage.model.Notification;

public class JHAppStateVariables {
	
	private static List<Notification> alerts = new ArrayList<Notification>();
	private static List<Notification> careTasks = new ArrayList<Notification>();
	
	public static JHTimelineActivity timeLineActivity;
	public static TextView alertNumberView;
	public static TextView careTaskNumberView;
	
	public static boolean addNotification(Notification notification ) {
		if (notification instanceof Alert){
			if ( !alerts.contains(notification) ) {
				alerts.add(notification);
				return true;
			}
		} else if (notification instanceof CareTask){
			if ( !careTasks.contains(notification) ) {
				careTasks.add(notification);
				return true;
			}
		} 
		return false;
	}
	
	public static int getAlertCount(){
		return alerts.size();
	}
	
	public static void removeAlert(String alertKey){
		Notification n = new Notification(alertKey);
		alerts.remove(n);
	}
	
	public static int getCareTaskCount() {
		return careTasks.size();
	}
	
	public static void removeCareTask(String alertKey){
		Notification n = new Notification(alertKey);
		careTasks.remove(n);
	}
	
	
	public static List<Notification> getNotifications(String type){
		if (type.equals(JHConstants.NOT_TYPE_ALERT)){
			return alerts;
		} else if (type.equals(JHConstants.NOT_TYPE_CARE_TASK)) {
			return careTasks;
		}
		return null;
	}
	
	
	private static String contactListString = "";
	
	public static void setContactsListString(String list) {
		JHAppStateVariables.contactListString = list;
	}
	
	public static String getContactsListString() {
		return contactListString;
	}
	
	private static List<Contact> contacts;

	public static List<Contact> getContacts() {
		return contacts;
	}

	public static void setContacts(List<Contact> contacts) {
		JHAppStateVariables.contacts = contacts;
	}
	
	private static String loginTocken = null;

	public static String getLoginTocken() {
		return loginTocken;
	}

	public static void setLoginTocken(String loginTocken) {
		JHAppStateVariables.loginTocken = loginTocken;
	}
	
	
	
}
