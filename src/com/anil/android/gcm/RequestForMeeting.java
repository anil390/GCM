package com.anil.android.gcm;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class RequestForMeeting extends Activity {
	
	private Button inviteeET;
	private Button subjectET;
	private Button dateET;
	private Button locationET;
	private Button timeET;

	private Button deleteBt;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.requestformeeting);
		subjectET = (Button) findViewById(R.id.sSubjectET);
	    dateET = (Button) findViewById(R.id.sDateET);
	    locationET = (Button) findViewById(R.id.sLocationET);
	    timeET = (Button) findViewById(R.id.sTimeET);
	    inviteeET = (Button) findViewById(R.id.sInviteeET);
	    
		Intent in = getIntent();
		String mes = in.getStringExtra("message");
		

		   JSONObject jsonResponse;
				try {
					Log.v("setValues called",mes);
						jsonResponse = new JSONObject(mes);
						JSONArray jsonMainNode = jsonResponse.optJSONArray("request");
			
						for (int i = 0; i < jsonMainNode.length(); i++) {
									    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
									    String subject = jsonChildNode.optString("subject");
									    String host = jsonChildNode.optString("host");
									    String location = jsonChildNode.optString("location");
									    String date = jsonChildNode.optString("date");
									    String time = jsonChildNode.optString("time");
									    Log.v("JSON","parsed");
									    Log.v("setValues called","inside for loop");
									    subjectET.setText(subject);
									    locationET.setText(location);
									    dateET.setText(date);
									    timeET.setText(time);
									    inviteeET.setText(host);
									   
				    
									}
				
		
	}
				catch(Exception e){
					Log.v("error", "catch block executed");
					
				}

}
}
