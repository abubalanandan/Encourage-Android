package com.jhl.encourage.utilities;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
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

	public static int currentRwPage = 0;

	private static List<String> sicknesses = new ArrayList<String>();

	public static void addSickenss(String sickness) {
		sicknesses.add(sickness);
	}

	public static void removeSickenss(String sickness) {
		sicknesses.remove(sickness);

	}

	private static List<String> emotionals = new ArrayList<String>();

	public static void addEmotionals(String emotional) {
		emotionals.add(emotional);
	}

	public static void removeEmotionals(String emotional) {
		emotionals.remove(emotional);

	}

	public static boolean isReadyForSubmission() {
		if (sicknesses.size() == 0 && emotionals.size() == 0) {
			return false;
		}
		return true;
	}

	public static String getSickEmoReport() {
		StringBuffer reportBuffer = new StringBuffer();
		for (String sickness : sicknesses) {
			reportBuffer.append(sickness);
			reportBuffer.append("~~");
		}

		for (String emotional : emotionals) {
			reportBuffer.append(emotional);
			reportBuffer.append("~~");
		}

		Log.d(JHConstants.LOG_TAG, reportBuffer.toString());

		return reportBuffer.toString();
	}

	public static boolean addNotification(Notification notification) {
		if (notification instanceof Alert) {
			if (!alerts.contains(notification)) {
				alerts.add(notification);
				return true;
			}
		} else if (notification instanceof CareTask) {
			if (!careTasks.contains(notification)) {
				careTasks.add(notification);
				return true;
			}
		}
		return false;
	}

	public static int getUnreadNotificationCount(String type) {
		List<Notification> oldList = null;
		List<Notification> newList = new ArrayList<Notification>();
		if (type.equals(JHConstants.NOT_TYPE_ALERT)) {
			oldList = alerts;
		} else if (type.equals(JHConstants.NOT_TYPE_CARE_TASK)) {
			oldList = careTasks;
		}
		for (Notification n : oldList) { // care tasks does not have a read
											// status
			if (n.getReadStatus() == null
					|| n.getReadStatus().equals(JHConstants.NOT_STATUS_UNREAD)) {
				newList.add(n);
			}
		}
		return newList.size();
	}

	public static void removeAlert(String alertKey) {
		Notification n = new Notification(alertKey);
		alerts.remove(n);
	}

	public static void removeCareTask(String alertKey) {
		Notification n = new Notification(alertKey);
		careTasks.remove(n);
	}

	public static List<Notification> getNotifications(String type) {
		if (type.equals(JHConstants.NOT_TYPE_ALERT)) {
			return alerts;
		} else if (type.equals(JHConstants.NOT_TYPE_CARE_TASK)) {
			return careTasks;
		}
		return null;
	}

	public static List<Notification> getUnreadNotifications(String type) {
		List<Notification> oldList = null;
		List<Notification> newList = new ArrayList<Notification>();
		if (type.equals(JHConstants.NOT_TYPE_ALERT)) {
			oldList = alerts;
		} else if (type.equals(JHConstants.NOT_TYPE_CARE_TASK)) {
			oldList = careTasks;
		}
		for (Notification n : oldList) { // care tasks does not have a read
											// status
			if (n.getReadStatus() == null
					|| n.getReadStatus().equals(JHConstants.NOT_STATUS_UNREAD)) {
				newList.add(n);
			}
		}

		return newList;
	}

	public static void markAsRead(String type, String key) {
		List<Notification> oldList = null;

		if (type.equals(JHConstants.NOT_TYPE_ALERT)) {
			oldList = alerts;
		} else if (type.equals(JHConstants.NOT_TYPE_CARE_TASK)) {
			oldList = careTasks;
		}
		for (Notification n : oldList) {
			if (n.getId().equals(key)) {
				n.setReadStatus(JHConstants.NOT_STATUS_READ);
				break;
			}
		}

		JHAppStateVariables.timeLineActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				JHAppStateVariables.alertNumberView.setText(JHAppStateVariables
						.getUnreadNotificationCount(JHConstants.NOT_TYPE_ALERT)
						+ "");
				JHAppStateVariables.careTaskNumberView.setText(JHAppStateVariables
						.getUnreadNotificationCount(JHConstants.NOT_TYPE_CARE_TASK)
						+ "");
			}
		});

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
		Log.d(JHConstants.LOG_TAG, "loginTocken " + loginTocken);
		return loginTocken;
	}

	public static void setLoginTocken(String loginTocken) {
		JHAppStateVariables.loginTocken = loginTocken;
	}

	public static void clearAppStateVariables() {
		loginTocken = null;
		alerts.clear();
		careTasks.clear();
		if (contacts != null)
			contacts.clear();
		contactListString = null;
		alertNumberView.setText("0");
		careTaskNumberView.setText("0");
		sicknesses.clear();
		emotionals.clear();
	}

}
