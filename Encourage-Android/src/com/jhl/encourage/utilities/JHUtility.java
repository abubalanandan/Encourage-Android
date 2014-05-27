package com.jhl.encourage.utilities;

import android.app.AlertDialog;
import android.content.Context;

public class JHUtility {
	public static void showDialogOk(String title, String message, Context context){
		new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton("Ok", null).create().show();
	}
}
