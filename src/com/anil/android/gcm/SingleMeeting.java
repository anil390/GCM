package com.anil.android.gcm;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class SingleMeeting extends Activity {
	private MeetingsModel datasource;
	//private String url = "http://abcd.co.in/gcmdemo/singlemeetng.php";
	//private String meetingId;
	Controller aController;
	public String returnedString;
	public int rMinutes;
	String jsonResult;
	private Button subjectET;
	private Button dateET;
	private Button locationET;
	private Button timeET;
	private TextView remainingTimeTV;
	private Button deleteBt;
	private Button inviteeET;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_meeting);
		Log.v("oncreate","oncreate called");
		 datasource = new MeetingsModel(getApplicationContext());
		    datasource.open();
		    Intent i = getIntent();
			String meetingId = i.getStringExtra("ID");
			//int tempId = Integer.parseInt(meetingId);
		    Meeting m = datasource.fetchMeeting(meetingId);
		    subjectET = (Button) findViewById(R.id.sSubjectET);
		    dateET = (Button) findViewById(R.id.sDateET);
		    locationET = (Button) findViewById(R.id.sLocationET);
		    timeET = (Button) findViewById(R.id.sTimeET);
		    inviteeET = (Button) findViewById(R.id.sInviteeET);
		    remainingTimeTV = (TextView) findViewById(R.id.textView4);
		  
		    locationET.setText(m.getLocation());
		    String str[] =m.getDate().split("-");
		    String strTime[] =m.getTime().split(":");
		    int hour = Integer.parseInt(strTime[0]);
		    int minute = Integer.parseInt(strTime[1]);
		    int day = Integer.parseInt(str[0]);
		    int month = Integer.parseInt(str[1]);
		    int year = Integer.parseInt(str[2]);
		    Calendar c = Calendar.getInstance(); 
            int curday=c.get(Calendar.DATE);
            int curmonth=c.get(Calendar.MONTH);
            int curyear=c.get(Calendar.YEAR);
            int curhour=c.get(Calendar.HOUR);
            int curminute=c.get(Calendar.MINUTE);
            
            
            if(day == curday && month == curmonth + 1){
            	int remainingHour;
            	int remainingMinute;
            	remainingTimeTV.setText("Remaining");
            	dateET.setText("Today");
            	dateET.setBackgroundColor(getResources().getColor(R.color.Red));
		            	if(hour > curhour)
		            	{
		            	remainingHour = hour - curhour;
		            	}
		            	else
		            	{
		            		remainingHour = curhour - hour;
		            	}
		            	
		            	
	            	if(minute > curminute)
	            	{
	            	remainingMinute = minute - curminute;
	            	}
	            	else
	            	{
	            		remainingMinute = curminute - minute;
	            	}
	            	
            	
	            	String remainingTime = Integer.toString(remainingHour)+":" + Integer.toString(remainingMinute);
	            			timeET.setText(remainingTime);
            
	            				
            }
            
            else if(day == curday + 1 && month == curmonth + 1){
            	dateET.setText("Tomorrow");
            	timeET.setText(m.getTime());
            	dateET.setBackgroundColor(getResources().getColor(R.color.Red));
            	
            }
            else
            {
            	dateET.setText(m.getDate());
            subjectET.setText(m.getSubject());
		    inviteeET.setText(m.getInvitee());
		    timeET.setText(m.getTime());
        	
            }
            subjectET.setText(m.getSubject());
		    inviteeET.setText(m.getInvitee());
		    
		/*
		aController = (Controller) getApplicationContext();
		// Getting name, email from intent
				Intent i = getIntent();
				meetingId = i.getStringExtra("ID");*/
		Log.d("meeting id","meeting displayed");
            
		/*JsonReadTask task = new JsonReadTask();
		   task.execute(new String[] { url });*/
	}
}
/*	
	private class JsonReadTask extends AsyncTask<String, Void, String> {
	
		  @SuppressWarnings("finally")
		@Override
		  protected String doInBackground(String... params) {
			 
	          BufferedReader reader=null;
			  try {
				String data = URLEncoder.encode("meetingId", "UTF-8")
				          + "=" + URLEncoder.encode(meetingId, "UTF-8");
				 URL url = new URL("http://abcd.co.in/gcmdemo/singlemeeting.php");
			            
			            URLConnection conn = url.openConnection();
			            conn.setDoOutput(true);
			            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			            wr.write( data );
			            wr.flush();
			            
			          reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			          StringBuilder sb = new StringBuilder();
			          String line = null;
			          
			          // Read Server Response
			          while((line = reader.readLine()) != null)
			              {
			                     // Append server response in string
			                     sb.append(line + "\n");
			              }
			              
			              
			              jsonResult = sb.toString();
			              Log.d("success",jsonResult);
			              return jsonResult;
			              
			  }
			              catch(Exception ex)
			              {
			                   
			              }
			              finally
			              {
			       
			                      try {
									reader.close();
								} catch (IOException e) {
									
									e.printStackTrace();
								}
			            
			                  return jsonResult;
			              }
		  }
			 
			  @Override
			  protected void onPostExecute(String jsonResult) {
				 // returnedString = result;
				 // Log.v("onPost",result);
				 
				  setValues();
			  				}
	}*/
/*
	public void setValues() {
		

			   JSONObject jsonResponse;
					try {
						Log.v("setValues called",jsonResult);
							jsonResponse = new JSONObject(jsonResult);
							JSONArray jsonMainNode = jsonResponse.optJSONArray("meetings");
				
							for (int i = 0; i < jsonMainNode.length(); i++) {
										    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
										    String id = jsonChildNode.optString("id");
										    String subject = jsonChildNode.optString("subject");
										    String location = jsonChildNode.optString("location");
										    String date = jsonChildNode.optString("date");
										    String time = jsonChildNode.optString("time");
										    Log.v("JSON","parsed");
										    Log.v("setValues called","inside for loop");
										    subjectET = (EditText) findViewById(R.id.sSubjectET);
											  subjectET.setText(jsonResult);
					    
										}

							} catch (JSONException e) {
									e.printStackTrace();
										}
		
	}*/
		
			  



