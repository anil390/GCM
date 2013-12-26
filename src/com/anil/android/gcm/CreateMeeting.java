package com.anil.android.gcm;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class CreateMeeting extends Activity {
	  static final int TIME_DIALOG_ID = 1111;
	  static final int DATE_DIALOG_ID = 1112;
	   
	    public Button btnTimePicker;
	    public Button btnDatePicker;
	    public Button btnMeetingCreated;
	    public EditText tLocationET;
	    public EditText tHostET;
	    public Button tDateET;
	    public Button tTimeET;
	    public EditText tinviteET;
	   
	    
	    private int hour;
	    private int minute;
	    private int month;
	    private int day;
	    private int tempyear;
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meeting_created);
		
		 addButtonClickListener();
	}
	
	 public void addButtonClickListener() {
		 
	        btnTimePicker = (Button) findViewById(R.id.timePickerBT);
	        btnDatePicker = (Button) findViewById(R.id.datePickerBT);
	        btnMeetingCreated = (Button) findViewById(R.id.meetingCreated);
	        
	        
	        btnMeetingCreated.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					tLocationET = (EditText) findViewById(R.id.meetingLocationET);
					tHostET = (EditText) findViewById(R.id.MeetingHostET);
					tinviteET = (EditText) findViewById(R.id.MeetingInviteET);
					tDateET = (Button) findViewById(R.id.datePickerBT);
					tTimeET = (Button) findViewById(R.id.timePickerBT);
					
					String tLocation = tLocationET.getText().toString();
					String tHost = tHostET.getText().toString();
					String tInvitee = tinviteET.getText().toString();
					String tDate = tDateET.getText().toString();
					String tTime = tTimeET.getText().toString();
					Intent i = new Intent(getApplicationContext(), MeetingCreated.class);
					i.putExtra("host", tHost);
					i.putExtra("location", tLocation);
					i.putExtra("invitee", tInvitee);
					i.putExtra("date", tDate);
					i.putExtra("time", tTime);
				
					startActivity(i);
					
					
					
					
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
	
}

