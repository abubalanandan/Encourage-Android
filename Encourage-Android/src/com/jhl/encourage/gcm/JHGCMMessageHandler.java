package com.jhl.encourage.gcm;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.jhl.encourage.activities.JHTimelineActivity;
import com.jhl.encourage.model.Alert;
import com.jhl.encourage.model.CareTask;
import com.jhl.encourage.model.Notification;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHNotificationParser;
import com.jhl.encourage.utilities.JHUtility;

import android.R;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class JHGCMMessageHandler extends IntentService {

    private String mes;
    private Handler handler;
    private Context context;
    
    public JHGCMMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        handler = new Handler();
        context = getApplicationContext();
        
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

       mes = extras.getString("content");
       //showToast();
       Log.i(JHConstants.LOG_TAG, "Received : (" +messageType+")  "+extras.getString("content"));
       JHNotificationParser parser = new JHNotificationParser () ;
       Notification n = parser.parse(mes);
    		   
       if( n != null ) {
       		boolean showLocalNotification = JHAppStateVariables.addNotification(n);
       		if(showLocalNotification){
       			showLocalNotification(n);
       			JHAppStateVariables.timeLineActivity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						JHAppStateVariables.alertNumberView.setText(JHAppStateVariables.getAlertCount()+"");
						JHAppStateVariables.careTaskNumberView.setText(JHAppStateVariables.getCareTaskCount() + "");
					}
				});
       		}
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
    
    public void showLocalNotification(final Notification no){
        handler.post(new Runnable() {
            public void run() {
            	NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            	 
                Intent intent = new Intent(context, JHTimelineActivity.class);
                 
                //use the flag FLAG_UPDATE_CURRENT to override any notification already there
                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
             
                android.app.Notification notification = new android.app.Notification(R.drawable.ic_delete, "Encourage Mobile Notification", System.currentTimeMillis());
                notification.flags = android.app.Notification.FLAG_AUTO_CANCEL | android.app.Notification.DEFAULT_LIGHTS | android.app.Notification.DEFAULT_SOUND;
             
                if(no instanceof Alert)
                	notification.setLatestEventInfo(context, "Encourage Mobile", "There is a new alert from Encurage Mobile", contentIntent);
                else if (no instanceof CareTask)
                	notification.setLatestEventInfo(context, "Encourage Mobile", "There is a new CareTask from Encurage Mobile", contentIntent);
                
                notificationManager.notify(10, notification);
            	
            }
         });
    }
}