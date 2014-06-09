package com.jhl.encourage.utilities;

import java.util.ArrayList;
import java.util.List;

import com.jhl.encourage.model.Alert;
import com.jhl.encourage.model.CareTask;
import com.jhl.encourage.model.Notification;

public class JHAppStateVariables {
	private static List<Notification> alerts = new ArrayList<Notification>();
	private static List<Notification> careTasks = new ArrayList<Notification>();
	
	public static void addNotification(Notification notification ) {
		if (notification instanceof Alert){
			if ( !alerts.contains(notification) ) {
				alerts.add(notification);
			}
		} else if (notification instanceof CareTask){
			if ( !careTasks.contains(notification) ) {
				careTasks.add(notification);
			}
		} 
	}
	
	public static List<Notification> getNotifications(String type){
		if (type.equals(JHConstants.NOT_TYPE_ALERT)){
			return alerts;
		} else if (type.equals(JHConstants.NOT_TYPE_CARE_TASK)) {
			return careTasks;
		}
		return null;
	}
}
