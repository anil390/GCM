package com.anil.android.gcm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class SingleMeeting extends Activity {
	private String url = "http://abcd.co.in/gcmdemo/singlemeetng.php";
	private String meetingId;
	Controller aController;
	public String returnedString;
	String jsonResult;
	private EditText subjectET;
	private EditText dateET;
	private EditText locationET;
	private EditText timeET;
	//private EditText subjectET;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_meeting);
		
		aController = (Controller) getApplicationContext();
		// Getting name, email from intent
				Intent i = getIntent();
				meetingId = i.getStringExtra("ID");
		Log.d("meeting id",meetingId);
			
		JsonReadTask task = new JsonReadTask();
		   task.execute(new String[] { url });
	}
	
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
	}

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
		
	}
		
			  

}

