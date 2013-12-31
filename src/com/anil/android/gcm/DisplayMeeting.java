package com.anil.android.gcm;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class DisplayMeeting extends Activity {
	Controller aController;
	AsyncTask<String, Void, Void> mAsyncTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aController = (Controller) getApplicationContext();
		setContentView(R.layout.meeting_display);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(aController);
		String tempMob = sp.getString("Mobile", "9535364187");
		new mAsyncTask().execute(tempMob);
	}
	
	protected class  mAsyncTask extends AsyncTask<String, Void, Void>{

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
	
	

}
