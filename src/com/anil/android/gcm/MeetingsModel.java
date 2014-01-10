
package com.anil.android.gcm;

	import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

	public class MeetingsModel {

	  // Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
	      MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_GCM_REG_ID, MySQLiteHelper.COLUMN_SUBJECT, MySQLiteHelper.COLUMN_DATE, MySQLiteHelper.COLUMN_TIME,MySQLiteHelper.COLUMN_INVITEE};

	  public MeetingsModel(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	    Log.v("SQLIte helper","initiated");
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	    Log.v("SQLIte helper","connection oppened");
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Meeting createMeeting(String gcm_reg_id, String subject, String location, String date, String time, String invitee) {
		  Log.v("SQLIte helper","meeting created started");
		  ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_GCM_REG_ID, gcm_reg_id);
	    values.put(MySQLiteHelper.COLUMN_SUBJECT, subject);
	    values.put(MySQLiteHelper.COLUMN_LOCATION, location);
	    values.put(MySQLiteHelper.COLUMN_DATE, date);
	    values.put(MySQLiteHelper.COLUMN_TIME, time);
	    values.put(MySQLiteHelper.COLUMN_INVITEE, invitee);
	   
	    long insertId = database.insert(MySQLiteHelper.TABLE_MEETINGS, null,
	        values);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_MEETINGS,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Meeting newMeeting = cursorToMeeting(cursor);
	    cursor.close();
	    return newMeeting;
	  }

	  public void deleteMeeting(Meeting meeting) {
	    long id = meeting.getId();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_MEETINGS, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public ArrayList<Meeting> getAllMeetings() {
	    ArrayList<Meeting> meetings = new ArrayList<Meeting>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_MEETINGS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Meeting meeting = cursorToMeeting(cursor);
	      meetings.add(meeting);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return meetings;
	  }

	  public Meeting cursorToMeeting(Cursor cursor) {
	    Meeting meeting = new Meeting();
	    meeting.setId(cursor.getInt(0));
	    meeting.setGcm_reg_id(cursor.getString(1));
	    meeting.setSubject(cursor.getString(2));
	    meeting.setLocation(cursor.getString(3));
	    meeting.setDate(cursor.getString(4));
	    meeting.setTime(cursor.getString(5));
	    meeting.setInvitee(cursor.getString(6));
	    
	    return meeting;
	  }
	} 