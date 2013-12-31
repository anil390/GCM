package com.anil.android.gcm;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateMeeting extends Activity {
	private static final int CONTACT_PICKER_RESULT = 1001; 
	  static final int TIME_DIALOG_ID = 1111;
	  static final int DATE_DIALOG_ID = 1112;
	   Controller aController;
	    public Button btnTimePicker;
	    public Button btnDatePicker;
	    public Button btnMeetingCreated;
	    public EditText tLocationET;
	    public EditText tHostET;
	    public Button tDateET;
	    public Button tTimeET;
	    public EditText tinviteET;
	    public Button contactPickerBT;
	   
	    
	    private int hour;
	    private int minute;
	    private int month;
	    private int day;
	    private int tempyear;
	    String host;
	   String reg_id;
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_meeting_activity);
		aController = (Controller) getApplicationContext();
		 addButtonClickListener();
	}
	
	 public void addButtonClickListener() {
		 
	        btnTimePicker = (Button) findViewById(R.id.timePickerBT);
	        btnDatePicker = (Button) findViewById(R.id.datePickerBT);
	        btnMeetingCreated = (Button) findViewById(R.id.meetingCreated);
	        contactPickerBT = (Button) findViewById(R.id.contact_picker);
	        contactPickerBT.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					/*Intent i = new Intent(getApplicationContext(), ContactsPickerActivity.class);
					startActivity(i);
					*/
					
					    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,  
					            Contacts.CONTENT_URI);  
					    startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);  
					
					
				}
			});
	        
	        btnMeetingCreated.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					tLocationET = (EditText) findViewById(R.id.meetingLocationET);
					/*tHostET = (EditText) findViewById(R.id.MeetingHostET);*/
					tinviteET = (EditText) findViewById(R.id.MeetingInviteET);
					tDateET = (Button) findViewById(R.id.datePickerBT);
					tTimeET = (Button) findViewById(R.id.timePickerBT);
					
					String tLocation = tLocationET.getText().toString();
					//String tHost = tHostET.getText().toString();
					String tInvitee = tinviteET.getText().toString();
					String tDate = tDateET.getText().toString();
					String tTime = tTimeET.getText().toString();
					SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(aController);
					
					host = sharedPreferences.getString("Mobile", null);
					reg_id = sharedPreferences.getString("Registration_Id", null);
										
					Intent i = new Intent(aController, MeetingCreated.class);
			
					i.putExtra("reg_id", reg_id);
					i.putExtra("host", host);
					
					i.putExtra("location", tLocation);
					i.putExtra("invitee", tInvitee);
					i.putExtra("date", tDate);
					i.putExtra("time", tTime);
					Log.d("Meeting", reg_id+host+tLocation+tInvitee+tDate+tTime);
					startActivity(i);
					finish();
					
					
					
				}
			});
	 
	        btnTimePicker.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	            	final TimePickerDialog dialog = new TimePickerDialog(CreateMeeting.this, timePickerListener, hour, minute,
	                        false);
	            	dialog.show();
	            	}
	 
	        });
	        btnDatePicker.setOnClickListener(new OnClickListener() {
	       	 
	            @Override
	            public void onClick(View v) {
	            	final DatePickerDialog dialog1 = new DatePickerDialog(CreateMeeting.this, datePickerListener, tempyear, month, day);
	            	dialog1.show();
	            	}
	 
	        });
	        
	        
	        
	 }      
	        @Override
	        protected Dialog onCreateDialog(int id) {
	            switch (id) {
	            case TIME_DIALOG_ID:
	                
	                return new TimePickerDialog(this, timePickerListener, hour, minute,
	                        false);
	     
	         
	            
	        case DATE_DIALOG_ID:
                
                // set time picker as current time
                return new DatePickerDialog(this, datePickerListener, tempyear, month, day);
     
            }
	            return null;
	        }
	 
	    
	  private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
	         
		  
	        @Override
	        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
	            hour   = hourOfDay;
	            minute = minutes;
	 
	            // set current time into output textview
	            btnTimePicker.setText(new StringBuilder().append(utilTime(hour))
	                    .append(":").append(utilTime(minute)));
	         }
	 
	    };
	    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
	         
		
	           

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					day = dayOfMonth;
					month = monthOfYear;
					tempyear = year;
				
				 btnDatePicker.setText(new StringBuilder().append(utilTime(day))
		                    .append("/").append(utilTime(month)).append("/").append(utilTime(tempyear)));
				
			}
	 
	    };
	
	    private static String utilTime(int value) {
	         
	        if (value < 10)
	            return "0" + String.valueOf(value);
	        else
	            return String.valueOf(value);
	    }
	    
	    
	    
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (resultCode == RESULT_OK) {  
	            switch (requestCode) {  
	            case CONTACT_PICKER_RESULT:  
	                Cursor cursor = null;  
	                String email = ""; 
	                String mobile = "";
	                
	                try {  
                    Uri result = data.getData();  
                    Log.v("ActivityResult", "Got a contact result: "  
                            + result.toString());  
                    // get the contact id from the Uri  
                    String id = result.getLastPathSegment();  
                    // query for everything email  
                    cursor = getContentResolver().query(Phone.CONTENT_URI,  
                            null, Phone.CONTACT_ID + "=?", new String[] { id },  
                            null);
                    int phoneIdx = cursor.getColumnIndex(Phone.DATA);  
                    // let's just get the first email  
                    if (cursor.moveToFirst()) {  
                        mobile = cursor.getString(phoneIdx);  
                        Log.v("ActivityResult", "Got Phone: " + mobile);  
                    } else {  
                        Log.w("ActivityResult", "No results");  
                    }  
                } catch (Exception e) {  
                    Log.e("ActivityResult", "Failed to get email data", e);  
                } finally {  
                    if (cursor != null) {  
                        cursor.close();  
                    }
	                
	                
	                /*try {  
	                    Uri result = data.getData();  
	                    Log.v("ActivityResult", "Got a contact result: "  
	                            + result.toString());  
	                    // get the contact id from the Uri  
	                    String id = result.getLastPathSegment();  
	                    // query for everything email  
	                    cursor = getContentResolver().query(Email.CONTENT_URI,  
	                            null, Email.CONTACT_ID + "=?", new String[] { id },  
	                            null);  
	                    int emailIdx = cursor.getColumnIndex(Email.DATA);  
	                    // let's just get the first email  
	                    if (cursor.moveToFirst()) {  
	                        email = cursor.getString(emailIdx);  
	                        Log.v("ActivityResult", "Got email: " + email);  
	                    } else {  
	                        Log.w("ActivityResult", "No results");  
	                    }  
	                } catch (Exception e) {  
	                    Log.e("ActivityResult", "Failed to get email data", e);  
	                } finally {  
	                    if (cursor != null) {  
	                        cursor.close();  
	                    }*/  
	                    tHostET = (EditText) findViewById(R.id.meetingInvitee); 
	                    tHostET.setText(mobile);  
	                    if (email.length() == 0) {  
	                        Toast.makeText(this, "No email found for contact.",  
	                                Toast.LENGTH_LONG).show();  
	                    }  
	                }
	                break;  
	            }  
	        } else {  
	            // gracefully handle failure  
	            Log.d("Contact picker", "Warning: activity result not ok");  
	        }  
	    } 
	
}

