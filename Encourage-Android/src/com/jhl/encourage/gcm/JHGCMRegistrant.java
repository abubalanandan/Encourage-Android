package com.jhl.encourage.gcm;

import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHUtility;

public class JHGCMRegistrant {
	
	public static void register(final Context conext){
		
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                GoogleCloudMessaging gcm = null;
        		String regid = "";
        		String PROJECT_NUMBER = "839257642692";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(conext);
                    }
                    regid = gcm.register(PROJECT_NUMBER);
                    msg = "Device registered, registration ID=" + regid;
                    Log.i(JHConstants.LOG_TAG,  msg);
                    
                    //call WS to register
                    

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                
            }
        }.execute(null, null, null);
    }
}
