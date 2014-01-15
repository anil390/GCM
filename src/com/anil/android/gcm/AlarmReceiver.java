package com.anil.android.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	 @Override
     public void onReceive(Context context, Intent intent)
     {
           	Log.v("Alarm", "Alarm Received");
            // String message="Hi I will be there later, See You soon";// message to send
             Toast.makeText(context, "Alarm Triggered and SMS Sent", Toast.LENGTH_LONG).show();
      }
	

/*	@Override
	public void onReceive(Context context, Intent intent) {
		 try {
		     Bundle bundle = intent.getExtras();
		     String message = bundle.getString("alarm_message");
		     Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		    } catch (Exception e) {
		     Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
		     e.printStackTrace();
		 
		    }
	}*/

}
