package com.jhl.encourage.utilities;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

public class JHUtility {

	public static void showDialogOk(String title, String message,
			Activity activity) {
		new AlertDialog.Builder(activity).setTitle(title).setMessage(message)
				.setPositiveButton("Ok", null).setCancelable(false).create()
				.show();
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {

			byte[] bytes = new byte[buffer_size];
			for (;;) {
				// Read byte from input stream

				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;

				// Write byte from output stream
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static String getTimeZoneString() {
		TimeZone tz = TimeZone.getDefault();
		Log.d(JHConstants.LOG_TAG, "timezone " + tz.getID());
		return tz.getID();
	}

	public static String getDateTime() {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = s.format(new Date());

		Log.d(JHConstants.LOG_TAG, "datetime " + date);
		return date;
	}
	public static String getDate() {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		String date = s.format(new Date());

		Log.d(JHConstants.LOG_TAG, "date " + date);
		return date;
	}
	
	public static String getFormattedDate() {
		SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy");
		String date = s.format(new Date());

		Log.d(JHConstants.LOG_TAG, "date " + date);
		return date;
	}
	
	public static String getUUID () {
		return UUID.randomUUID().toString();
	}
}
