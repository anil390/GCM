package com.anil.android.gcm;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class DisplayAllMeetings extends ListActivity {
	
	private MeetingsModel datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    setContentView(R.layout.meeting_display);
	    Log.v("Set","content view set");
	    datasource = new MeetingsModel(getApplicationContext());
	    datasource.open();
	    
	    Meeting  test1 = datasource.createMeeting("eertwer", "tewter", "hello", "cation", "ate");
	    List<Meeting> values = datasource.getAllMeetings();

	    ArrayAdapter<Meeting> adapter = new ArrayAdapter<Meeting>(this,
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
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

	} 

