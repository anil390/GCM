package com.anil.android.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
	Button ldashboardButton;
	
	public static String tname;
	public static String temail;
	public static String tphone_number;
	public static String tdev_reg_id;

	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		aController = (Controller) getApplicationContext();
		// Getting name, email from intent
				Intent i = getIntent();
				tphone_number = i .getStringExtra("phone_number");
				tname = i.getStringExtra("name");
				temail = i.getStringExtra("email");
				
				
				// Check if Internet present
				if (!aController.isConnectingToInternet()) {
					
					// Internet Connection is not present
					aController.showAlertDialog(MainActivity.this,
							"Internet Connection Error",
							"Please connect to Internet connection", false);
					// stop executing code by return
					return;
				}
				
				GCMRegistrar.checkDevice(this);
				GCMRegistrar.checkManifest(this);
				tdev_reg_id = GCMRegistrar.getRegistrationId(this);
				
				if (tdev_reg_id.equals("")) {
					
					// Register with GCM			
					GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);
					
					
				} else {
					
					if (GCMRegistrar.isRegisteredOnServer(this)) {
					Toast.makeText(getApplicationContext(), "Already registered with GCM Server", Toast.LENGTH_LONG).show();
					
					} else {
						
						final Context context = this;
						mRegisterTask = new AsyncTask<Void, Void, Void>() {

							@Override
							protected Void doInBackground(Void... params) {
								
								Log.d("background activity", "bg started");
								aController.register(context, tphone_number, tname, temail, tdev_reg_id);
								
								return null;
							}

							@Override
							protected void onPostExecute(Void result) {
								Log.d("background activity", "bg end");
								mRegisterTask = null;
							}

						};
						
						mRegisterTask.execute(null, null, null);
					}
				}
				
		setContentView(R.layout.activity_main);
		ldashboardButton = (Button) findViewById(R.id.goto_dashboard);
		
		ldashboardButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Dashboard.class);
				startActivity(i);
				
			}
		});
		lblMessage = (TextView) findViewById(R.id.lblMessage);
		
	
	}		

	
	@Override
	protected void onDestroy() {
		// Cancel AsyncTask
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			
			GCMRegistrar.onDestroy(this);
			
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

}
