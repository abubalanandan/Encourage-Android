package com.jhl.encourage.utilities;

import android.app.Activity;
import android.app.AlertDialog;

public class JHUtility {
	public static void showDialogOk(String title, String message, Activity activity){
		new AlertDialog.Builder(activity).setTitle(title).setMessage(message).setPositiveButton("Ok", null).setCancelable(false).create().show();
	}
}
