package com.anil.android.gcm;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {
	
	// UI elements
	EditText txtName; 
	EditText txtEmail;
	EditText txtPhone;
	Controller aController;
	
	// Register button
	Button btnRegister;
	Boolean check = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		aController = (Controller) getApplicationContext();
		
		Log.d("DBcheck", "checking database");
		check = checkDataBase();
		
		if(check)
		{
				Log.d("DBcheck", "database exists");
				/*DbHelper DBhelper = new DbHelper(getApplicationContext());
				Cursor cur = DBhelper.fetchRegid();
				cur.moveToFirst();
				String id = cur.getString( cur.getColumnIndexOrThrow("email")); 
				String did = cur.getString( cur.getColumnIndexOrThrow("dev_reg_id"));// id is first column in db
				Log.d("cursor", "cursor returned" + id  + did);
				*/
/*

			        cur.moveToFirst();

			        new ArrayList<String>();

			        do {

			              TextView txtId=(TextView)findViewById(R.id.txtID);

			              txtId.append(cur.getString(0)+"     "+cur.getString(1)+" "+cur.getString(2)+"     "+cur.getString(3)+ "\n");

			        }while (cur.moveToNext());*/
				
				//SQLiteDatabase db = SQLiteDatabase.openDatabase(Config.DB_PATH + Config.DB_NAME, null, 0);
			Intent inew = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(inew);
			
		}
		else{
			Log.d("DBcheck", "database does not exists");	
		setContentView(R.layout.activity_register);
		
		//Get Global Controller Class object (see application tag in AndroidManifest.xml)
		final Controller aController = (Controller) getApplicationContext();
		
		// Check if Internet Connection present
		if (!aController.isConnectingToInternet()) {
			
			// Internet Connection is not present
			aController.showAlertDialog(RegisterActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			
			// stop executing code by return
			return;
		}

	/*	// Check if GCM configuration is set
		if (Config.YOUR_SERVER_URL == null || Config.GOOGLE_SENDER_ID == null || Config.YOUR_SERVER_URL.length() == 0
				|| Config.GOOGLE_SENDER_ID.length() == 0) {
			
			// GCM sernder id / server url is missing
			aController.showAlertDialog(RegisterActivity.this, "Configuration Error!",
					"Please set your Server URL and GCM Sender ID", false);
			
			// stop executing code by return
			 return;
			 }
			 */
		
		
		txtName = (EditText) findViewById(R.id.txtName);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtPhone = (EditText) findViewById(R.id.txtNum);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		
		// Click event on Register button
		btnRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {  
				// Get data from EditText 
				String mname = txtName.getText().toString(); 
				String memail = txtEmail.getText().toString();
				String mphone_number = txtPhone.getText().toString();
				
				// Check if user filled the form
				if(mname.trim().length() > 0 && memail.trim().length() > 0){
					
					// Launch Main Activity
					Intent i = new Intent(getApplicationContext(), MainActivity.class);
					i.putExtra("phone_number", mphone_number);
					i.putExtra("name", mname);
					i.putExtra("email", memail);
					
					startActivity(i);
					finish();
					
				}else{
					
					// user doen't filled that data
					aController.showAlertDialog(RegisterActivity.this, "Registration Error!", "Please enter your details", false);
				}
			}
		});
		}
	}
	
	
	 private boolean checkDataBase(){
		 
		 SQLiteDatabase checkDB = null;
		  
		 try{
		 String myPath = Config.DB_PATH + Config.DB_NAME;
		 checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		  
		 }catch(SQLiteException e){
		  
		 Log.d("DBchecker", "catch block executed");
		  
		 }
		  
		 if(checkDB != null){
		  
		 checkDB.close();
		  
		 }
		  
		 return checkDB != null ? true : false;
		 }
	
	
	

}
