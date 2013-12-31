package com.anil.android.gcm;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class HomeScreen extends Activity {
	Controller aController;
	TextView lblMessage1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		aController = (Controller) getApplicationContext();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_home);
		lblMessage1 = (TextView) findViewById(R.id.lblMessage1);
		Log.d("Registering", "message handler registered");
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				Config.DISPLAY_MESSAGE_ACTION));
		
	}
	
	
	
private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("Reciever", "message received");
			
			String newMessage = intent.getExtras().getString(Config.EXTRA_MESSAGE);
			
			// Waking up mobile if it is sleeping
			aController.acquireWakeLock(getApplicationContext());
			
			// Display message on the screen
			lblMessage1.append(newMessage + "\n");			
			
			Toast.makeText((Controller) getApplicationContext(), "Got Message: " + newMessage, Toast.LENGTH_LONG).show();
			
			// Releasing wake lock
			aController.releaseWakeLock();
		}
	};
	
	@Override
	protected void onDestroy() {
		
		try {
			// Unregister Broadcast Receiver
			unregisterReceiver(mHandleMessageReceiver);
			
			//Clear internal resources.
			GCMRegistrar.onDestroy(this);
			
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

}
