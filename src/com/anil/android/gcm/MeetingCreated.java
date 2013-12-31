package com.anil.android.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MeetingCreated extends Activity {
	Controller aController;
	
	AsyncTask<Void, Void, Void> mMeetingCreationTask;
	public static String mhost;
	public static String mlocation;
	public static String minvitee;
	public static String mdate;
	public static String mtime;
	
	private static String mreg_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		aController = (Controller) getApplicationContext();
		setContentView(R.layout.created_meeting);
		
		Intent i = getIntent();
		mreg_id =i.getStringExtra("reg_id");
		mhost = i.getStringExtra("host");
		mlocation = i.getStringExtra("location");
		minvitee = i.getStringExtra("invitee");
		mdate = i.getStringExtra("date");
		mtime = i.getStringExtra("time");
		Log.d("MeetingCreated", mreg_id+mhost+mlocation+minvitee+mdate+mtime);
		
		final Context context = this;
		mMeetingCreationTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				
				// Register on our server
				// On server creates a new user
				Log.d("background activity", "bg started");
				aController.createMeeting(context, mreg_id, mhost, mlocation, minvitee, mdate, mtime);
				
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				Log.d("Meeting created", "created meeting");
				mMeetingCreationTask = null;
			}

		};
		
		
		mMeetingCreationTask.execute(null, null, null);
	}
	
	
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}
	
	

}
