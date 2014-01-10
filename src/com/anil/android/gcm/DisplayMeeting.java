package com.anil.android.gcm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMeeting extends Activity {
	
	public ArrayList<ItemModel> meetingsList;
	 private String jsonResult;
	 private String url = "http://abcd.co.in/gcmdemo/fetchMeetings.php";
	 private ListView listView;
	 Controller aController;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
			aController = (Controller) getApplicationContext();
			setContentView(R.layout.meeting_display); 
			listView = (ListView) findViewById(R.id.listView1);
			accessWebService();
	 }
	 public void accessWebService() {
		  JsonReadTask task = new JsonReadTask();
		   task.execute(new String[] { url });
		 }
	// end async task
	 
	 public void ListDrwaer() {
		 /* ArrayList<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();*/
		 meetingsList = new ArrayList<ItemModel>();
		  try {
		   JSONObject jsonResponse = new JSONObject(jsonResult);
		   JSONArray jsonMainNode = jsonResponse.optJSONArray("meetings");
		 
		   for (int i = 0; i < jsonMainNode.length(); i++) {
		    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
		    String id = jsonChildNode.optString("id");
		    String subject = jsonChildNode.optString("subject");
		    String date = jsonChildNode.optString("date");
		    int tempId = Integer.parseInt(id);
		    ItemModel _meeting = new ItemModel(tempId,subject,date); 
		    meetingsList.add(_meeting);
		  
		   }
		  } catch (JSONException e) {
		   Toast.makeText(getApplicationContext(), "Error" + e.toString(),
		     Toast.LENGTH_SHORT).show();
		  }
		   MyCustomAdapter dataAdapter = new MyCustomAdapter(this,R.layout.list_item_meeting, meetingsList);
            listView = (ListView) findViewById(R.id.listView1);
            Log.d("adapter","defore adapter");
            listView.setAdapter(dataAdapter);
            Log.d("adapter","after adapter");
            listView.setOnItemClickListener(new OnItemClickListener()
            {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                    	 ItemModel m = (ItemModel) parent.getItemAtPosition(position);
                    	Intent singleMeeting = new Intent(getApplicationContext(),SingleMeeting.class);
                        int tempId = m.getId();
                        
                        String tempStr =  Integer.toString(tempId);
                    	singleMeeting.putExtra("ID", tempStr) ; 
                        Log.v("on display","before displaying single activity");
                        startActivity(singleMeeting);
                    	
                    }
            });
	 
	 }
            
            private class MyCustomAdapter extends ArrayAdapter<ItemModel>
            {
             
               private ArrayList<ItemModel> meetingsList;
             
              public MyCustomAdapter(Context context, int textViewResourceId,
             
              ArrayList<ItemModel> meetingsList)
              {
                    super(context, textViewResourceId, meetingsList);
                    this.meetingsList = new ArrayList<ItemModel>();
                    this.meetingsList.addAll(meetingsList);
              }
             
                private class ViewHolder
                {
                  TextView dId;
                  TextView dSubject;
                  TextView dDate;
                }
             
                @Override
               public View getView(int position, View convertView, ViewGroup parent)
                {
             
                        ViewHolder holder = null;
             
                        Log.v("ConvertView", String.valueOf(position));
             
                        if (convertView == null)
                        {
                        	
                           LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             
                           convertView = vi.inflate(R.layout.list_item_meeting, null);
             
                          holder = new ViewHolder();
                          holder.dId = (TextView) convertView.findViewById(R.id.idTV);
                          holder.dSubject = (TextView) convertView.findViewById(R.id.subjectTV);
                          holder.dDate = (TextView) convertView.findViewById(R.id.dateTV);
                         
             
                          convertView.setTag(holder);
             /*
                                    holder.name.setOnClickListener( new View.OnClickListener()
                                    {
                                               public void onClick(View v)  
                                               {
                                                 CheckBox cb = (CheckBox) v;
                                                 States _state = (States) cb.getTag();
             
                                                 Toast.makeText(getApplicationContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(),
                                                 Toast.LENGTH_LONG).show();
             
                                                 _state.setSelected(cb.isChecked());
                                              }
                                    });*/
             
                        }
                        else
                        {
                            holder = (ViewHolder) convertView.getTag();
                        }
             
                        ItemModel meet = meetingsList.get(position);
                        Log.d("check1","setting text");
                        Integer tem = meet.getId();
                        String stemp = tem.toString();
                        holder.dId.setText(stemp);
                        holder.dSubject.setText(meet.getSubject());
                        holder.dDate.setText(meet.getDate());
                     return convertView;
                }
		 
	
		 }
		

		 // Async Task to access the web
		 private class JsonReadTask extends AsyncTask<String, Void, String> {
		  @SuppressWarnings("finally")
		@Override
		  protected String doInBackground(String... params) {
			 
	          BufferedReader reader=null;
			  try {
				  
				  SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(aController);
					
					String host1 = sharedPreferences.getString("Mobile", null);
				String data = URLEncoder.encode("host", "UTF-8")
				          + "=" + URLEncoder.encode(host1, "UTF-8");
				 
			            
			              // Defined URL  where to send data
			              URL url = new URL("http://abcd.co.in/gcmdemo/fetchMeetings.php");
			               
			           // Send POST data request
			 
			            URLConnection conn = url.openConnection();
			            conn.setDoOutput(true);
			            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			            wr.write( data );
			            wr.flush();
			        
			            // Get the server response
			             
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
			  protected void onPostExecute(String result) {
			   ListDrwaer();
			  }
			 }
	}
	

