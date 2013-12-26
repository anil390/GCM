package com.anil.android.gcm;

import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MeetingCreated extends Activity {
	Controller aController;
	
	AsyncTask<Void, Void, Void> mMeetingCreationTask;
	public static String host;
	public static String location;
	public static String invitee;
	public static String date;
	public static String time;
	
	public static String reg_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meeting_created);
		aController = (Controller) getApplicationContext();
		Intent i = getIntent();
		host = i.getStringExtra("host");
		location = i.getStringExtra("location");
		invitee = i.getStringExtra("invitee");
		date = i.getStringExtra("date");
		time = i.getStringExtra("time");
		reg_id = GCMRegistrar.getRegistrationId(this);
		
		final Context context = this;
		mMeetingCreationTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				
				// Register on our server
				// On server creates a new user
				Log.d("background activity", "bg started");
				aController.createMeeting(context, reg_id, host, location, invitee, date, time);
				
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				Log.d("background activity", "bg end");
				mMeetingCreationTask = null;
			}

		};
		
		// execute AsyncTask
		mMeetingCreationTask.execute(null, null, null);
		
		
		
		
		
		
		
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	

}
