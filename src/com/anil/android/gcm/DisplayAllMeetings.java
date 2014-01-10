package com.anil.android.gcm;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayAllMeetings extends ListActivity {
	
	private MeetingsModel datasource;
	public ArrayList<Meeting> meetingsList;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meeting_display);
	    listView = (ListView) findViewById(R.id.listView1);
	    Log.v("Set","content view set");
	    datasource = new MeetingsModel(getApplicationContext());
	    datasource.open();
	    
	   // Meeting  test1 = datasource.createMeeting("eertwer", "tewter", "hello", "cation", "ate", "jygj");
	    
	   ArrayList<Meeting> values = datasource.getAllMeetings();
	   MyCustomAdapter dataAdapter = new MyCustomAdapter(this,R.layout.list_item_meeting, values);
      
        Log.d("adapter","defore adapter");
        listView.setAdapter(dataAdapter);
      /*  listView.setOnItemClickListener(new OnItemClickListener()
        {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                	 Meeting m = (Meeting) parent.getItemAtPosition(position);
                	Intent singleMeeting = new Intent(getApplicationContext(),SingleMeeting.class);
                    int tempId = m.getId();
                    
                    String tempStr =  Integer.toString(tempId);
                	singleMeeting.putExtra("ID", tempStr) ; 
                    Log.v("on display","before displaying single activity");
                    startActivity(singleMeeting);
                	
                }
        });*/
        

	    /*ArrayAdapter<Meeting> adapter = new ArrayAdapter<Meeting>(this,
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);*/
	  }
	  @Override
	  protected void onResume() {
	    datasource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    datasource.close();
	    super.onPause();
	  }
	  
	  
	  private class MyCustomAdapter extends ArrayAdapter<Meeting>
      {
       
         private ArrayList<Meeting> meetingsList;
       
        public MyCustomAdapter(Context context, int textViewResourceId,ArrayList<Meeting> meetingsList)
        {
              super(context, textViewResourceId, meetingsList);
              this.meetingsList = new ArrayList<Meeting>();
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
       
                  Meeting meet = meetingsList.get(position);
                  Log.d("check1","setting text");
                  //Integer tem = meet.getId();
                  //String stemp = tem.toString();
                 // holder.dId.setText(meet.getId());
                  holder.dSubject.setText(meet.getLocation());
                  holder.dDate.setText(meet.getDate());
               return convertView;
          }
	 

	 }
	  
	  

	} 

