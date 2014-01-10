package com.anil.android.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MeetingCreated extends Activity {
	Controller aController;
	private MeetingsModel datasource;
	
	AsyncTask<Void, Void, Void> mMeetingCreationTask;
	public static String mhost;
	public static String mlocation;
	public static String minvitee;
	public static String mSubject;
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
		mSubject = i.getStringExtra("subject");
		mlocation = i.getStringExtra("location");
		minvitee = i.getStringExtra("invitee");
		mdate = i.getStringExtra("date");
		mtime = i.getStringExtra("time");
		
		//saving data into SQLite
		  datasource = new MeetingsModel(getApplicationContext());
		  datasource.open();
		  Meeting  test1 = datasource.createMeeting(mreg_id, mSubject, mlocation, mdate, mtime,minvitee);
		  Log.v("Success","meeting created successfully");
		 // Date saved in SQLite   
		    
		    
		    
		/*  //Setting Alarm  
		 AlarmManager am=(AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
         Intent in = new Intent(getApplicationContext(), Alarm.class);
         PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, in, 0);
         am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 1, pi); 
       */
         
         
		final Context context = this;
		mMeetingCreationTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
			
				aController.createMeeting(context, mreg_id, mhost, mlocation, mSubject, mdate, mtime, minvitee);
				
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

//*******************************************************************************************************





//Alarm successfully set


		/*String mtempformat = mdate+ "" +mtime;
		//Setting Alarm
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy hh:mm");
		Date parsedDate = null;
		try {
			parsedDate = format.parse(mtempformat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long alertTime = now.getTime() - parsedDate.getTime();

		Intent someIntent = new Intent(getApplicationContext(), Dashboard.class);
		PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 0, someIntent, 0);

		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, alertTime , pending); 
		Toast.makeText(getApplicationContext(), "Alarm set", Toast.LENGTH_LONG).show();*/
		
		//Sending data to server
