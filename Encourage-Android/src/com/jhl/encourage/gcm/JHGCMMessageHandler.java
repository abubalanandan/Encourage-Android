package com.jhl.encourage.gcm;

import java.util.List;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.jhl.encourage.model.Notification;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHNotificationParser;
import com.jhl.encourage.utilities.JHUtility;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class JHGCMMessageHandler extends IntentService {

     String mes;
     private Handler handler;
    public JHGCMMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        handler = new Handler();
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

       mes = extras.getString("title");
       //showToast();
       Log.i(JHConstants.LOG_TAG, "Received : (" +messageType+")  "+extras.getString("title"));
       JHNotificationParser parser = new JHNotificationParser () ;
       Notification n = parser.parse(mes);
    		   
       if( n != null ) {
       		JHAppStateVariables.addNotification(n);
       }
       
       Log.i(JHConstants.LOG_TAG, "Alerts" );
       
       for ( Notification not : JHAppStateVariables.getNotifications(JHConstants.NOT_TYPE_ALERT) ) {
    	   Log.i(JHConstants.LOG_TAG, "Alert " + not);
       }
       
       Log.i(JHConstants.LOG_TAG, "CareTasks" ); 
       
       for ( Notification not : JHAppStateVariables.getNotifications(JHConstants.NOT_TYPE_CARE_TASK) ) {
    	   Log.i(JHConstants.LOG_TAG, "CareTask " + not);
       }
       
       JHGCMBroadcastReceiver.completeWakefulIntent(intent);

    }

    public void showToast(){
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(),mes , Toast.LENGTH_LONG).show();
            }
         });

    }
}