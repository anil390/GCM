package com.anil.android.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";
	
	private Controller aController = null;

    public GCMIntentService() {
    	// Call extended class Constructor GCMBaseIntentService
        super(Config.GOOGLE_SENDER_ID);
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
    	
    	//Get Global Controller Class object (see application tag in AndroidManifest.xml)
    	if(aController == null)
           aController = (Controller) getApplicationContext();
    	
        Log.i(TAG, "Device registered: regId = " + registrationId);
        aController.displayMessageOnScreen(context, "Your device registred with GCM");
        Log.d("NAME", MainActivity.name);
        aController.register(context, MainActivity.name, MainActivity.email, registrationId);
    }

    /**
     * Method called on device unregistred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
    	if(aController == null)
            aController = (Controller) getApplicationContext();
        Log.i(TAG, "Device unregistered");
        aController.displayMessageOnScreen(context, getString(R.string.gcm_unregistered));
        aController.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message from GCM server
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
    	
  
        if(aController == null)
            aController = (Controller) getApplicationContext();
         
        Log.i(TAG, "Received message");
        String message = intent.getExtras().getString("price");
         
        aController.displayMessageOnScreen(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
    	
    	if(aController == null)
            aController = (Controller) getApplicationContext();
    	
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        aController.displayMessageOnScreen(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
    	
    	if(aController == null)
            aController = (Controller) getApplicationContext();
    	
        Log.i(TAG, "Received error: " + errorId);
        aController.displayMessageOnScreen(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
    	
    	if(aController == null)
            aController = (Controller) getApplicationContext();
    	
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        aController.displayMessageOnScreen(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Create a notification to inform the user that server has sent a message.
     */
    
    private static void generateNotification(Context context, String message) {
      //  int icon = R.drawable.ic_launcher;
       // long when = System.currentTimeMillis();
        NotificationCompat.Builder builder =  
                new NotificationCompat.Builder(context)  
                .setSmallIcon(R.drawable.ic_launcher)  
                .setContentTitle("Notifications")  
                .setContentText(message);  

        Intent notificationIntent = new Intent(context, MainActivity.class);  
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,   
                PendingIntent.FLAG_UPDATE_CURRENT);  
        builder.setContentIntent(contentIntent);  

        // Add as notification  
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);  
        manager.notify(0, builder.build());  
    }  
     
         /*   NotificationCompat.Builder builder =  
                    new NotificationCompat.Builder(context)  
                    .setSmallIcon(R.drawable.ic_launcher)  
                    .setContentTitle("Notifications Example")  
                    .setContentText("This is a test notification");  

            Intent notificationIntent = new Intent(context, MainActivity.class);  
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,   
                    PendingIntent.FLAG_UPDATE_CURRENT);  
            builder.setContentIntent(contentIntent);  

            // Add as notification  
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);  
            manager.notify(0, builder.build());  */
         
}
       /* NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                
                .setContentTitle("My notification")
                .setContentText("Hello World!");*/

      //  Notification notification = new Notification(icon, message, when);
        
      //  String title = context.getString(R.string.app_name);
        
       // Intent notificationIntent = new Intent(context, MainActivity.class);
        // set intent so it does not start a new activity
       // n/otificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
             //   Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //PendingIntent intent =
             //   PendingIntent.getActivity(context, 0, notificationIntent, 0);
      //  notification.setLatestEventInfo(context, title, message, intent);
      //  notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        // Play default notification sound
       // notification.defaults |= Notification.DEFAULT_SOUND;
        
        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
        
        // Vibrate if vibrate is enabled
       // notification.defaults |= Notification.DEFAULT_VIBRATE;
       // notificationManager.notify(0, notification);  
    
 
