package com.anil.android.gcm;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends Activity {

	TextView lblMessage;
	Controller aController;
	AsyncTask<Void, Void, Void> mRegisterTask;
	Button lCreateButton;
	
	//String tempRegid;
	
	public static String tname;
	public static String temail;
	public static String tphone_number;
	public static String tdev_reg_id;

	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lCreateButton = (Button) findViewById(R.id.create_meetingBT);
		lCreateButton = (Button) findViewById(R.id.create_meetingBT);
		lCreateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), CreateMeeting.class);
				startActivity(i);
				
			}
		});
		lblMessage = (TextView) findViewById(R.id.lblMessage);
		
		
		
		// Get Global Controller Class object (see application tag in AndroidManifest.xml)
		aController = (Controller) getApplicationContext();
		/*
		
		// Check if Internet present
		if (!aController.isConnectingToInternet()) {
			
			// Internet Connection is not present
			aController.showAlertDialog(MainActivity.this,
					"Internet Connection Error",
					"Please connect to Internet connection", false);
			// stop executing code by return
			return;
		}
		*/
		// Getting name, email from intent
		Intent i = getIntent();
		tphone_number = i .getStringExtra("phone_number");
		tname = i.getStringExtra("name");
		temail = i.getStringExtra("email");
		
		
		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest permissions was properly set 
		GCMRegistrar.checkManifest(this);

		//lblMessage = (TextView) findViewById(R.id.lblMessage);
		
		// Register custom Broadcast receiver to show messages on activity
		/*registerReceiver(mHandleMessageReceiver, new IntentFilter(
				Config.DISPLAY_MESSAGE_ACTION));*/
		
		// Get GCM registration id
		tdev_reg_id = GCMRegistrar.getRegistrationId(this);
		
		Log.d("Regi id", "your device id" + tdev_reg_id);
		//String tempString = regId;
		
		//saving data to database

		
		
		
		
		
		
		
		

		// Check if regid already presents
		if (tdev_reg_id.equals("")) {
			
			// Register with GCM			
			GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);
			
			
		} else {
			
			// Device is already registered on GCM Server
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				
				try
				{
				Log.d("DbHelper", "DB class started");
	            DbHelper DBhelper = new DbHelper(getApplicationContext());
	            
	            SQLiteDatabase db = DBhelper.getWritableDatabase();
	            //put variables
	            ContentValues values = new ContentValues();
	            //values.put(DatabaseHelper.COLUMN_ID, 1);
	           values.put(DbHelper.COLUMN_PHONE, tphone_number);
	            values.put(DbHelper.COLUMN_NAME, tname);
	            values.put(DbHelper.COLUMN_EMAIL, temail);
	            values.put(DbHelper.COLUMN_REGID, tdev_reg_id);

	            long query = db.insert(DbHelper.TABLE_NAME, null, values);
	            String temp = Long.toString(query);
	            Log.d("DbHElper",temp);
	            db.close();
	        }
	        catch(Exception e)
	        {
	          Log.d("DBHelper class " , "catch block executed");
	          e.printStackTrace();
	        }
				
				
				
							
				Toast.makeText(getApplicationContext(), "Already registered with GCM Server", Toast.LENGTH_LONG).show();
			
			} else {
				
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						
						// Register on our server
						// On server creates a new user
						Log.d("background activity", "bg started");
						aController.register(context, tname, temail, tdev_reg_id);
						
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						Log.d("background activity", "bg end");
						mRegisterTask = null;
					}

				};
				
				// execute AsyncTask
				mRegisterTask.execute(null, null, null);
			}
		}
	}		


	// Create a broadcast receiver to get message and show on screen 
/*	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			String newMessage = intent.getExtras().getString(Config.EXTRA_MESSAGE);
			
			// Waking up mobile if it is sleeping
			aController.acquireWakeLock(getApplicationContext());
			
			// Display message on the screen
			lblMessage.append(newMessage + "\n");			
			
			Toast.makeText(getApplicationContext(), "Got Message: " + newMessage, Toast.LENGTH_LONG).show();
			
			// Releasing wake lock
			aController.releaseWakeLock();
		}
	};*/
	
	@Override
	protected void onDestroy() {
		// Cancel AsyncTask
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			// Unregister Broadcast Receiver
			//unregisterReceiver(mHandleMessageReceiver);
			
			//Clear internal resources.
			GCMRegistrar.onDestroy(this);
			
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

}
