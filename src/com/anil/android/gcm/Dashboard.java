package com.anil.android.gcm;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Dashboard extends Activity {
	Controller aController;
	Button createMeeting;
	Button viewMeeting;
	TextView wellcomeTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aController = (Controller) getApplicationContext();
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		String reg_id = sharedPreferences.getString("Registration_Id", null);
		if (reg_id == null)
		{
			Intent i = new Intent(aController,RegisterActivity.class);
			startActivity(i);
			return ;
		}		
		
		setContentView(R.layout.dashboard);
		ActionBar actionBar = getActionBar();
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				Config.DISPLAY_MESSAGE_ACTION));
		
        actionBar.setDisplayHomeAsUpEnabled(true);
		createMeeting = (Button) findViewById(R.id.createMeetingBT);
		viewMeeting = (Button) findViewById(R.id.viewMeetingsBT);
		wellcomeTV = (TextView) findViewById(R.id.welcome);
		wellcomeTV.setText("Welcome Again Anil !");
		createMeeting.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),CreateMeeting.class);
				startActivity(i);
				
			}
		});
		viewMeeting.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),DisplayMeeting.class);
				startActivity(in);
				
			}
		});
		
		
		
	}
	
	
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			String newMessage = intent.getExtras().getString(Config.EXTRA_MESSAGE);
			
			// Waking up mobile if it is sleeping
			aController.acquireWakeLock(getApplicationContext());
			
			// Display message on the screen
			//lblMessage.append(newMessage + "\n");			
			
			Toast.makeText(getApplicationContext(), "Got Message: " + newMessage, Toast.LENGTH_LONG).show();
			
			// Releasing wake lock
			aController.releaseWakeLock();
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.activity_main_actions, menu);
	 
	        return super.onCreateOptionsMenu(menu);
	    }
	

}
