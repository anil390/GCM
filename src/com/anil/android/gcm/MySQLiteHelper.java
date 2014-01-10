package com.anil.android.gcm;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_MEETINGS = "meetings";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_GCM_REG_ID = "gcm_reg_id";
  public static final String COLUMN_SUBJECT = "subject";
  public static final String COLUMN_LOCATION = "location";
  public static final String COLUMN_DATE = "date";
  public static final String COLUMN_TIME = "time";
  public static final String COLUMN_INVITEE = "invitee";
  
  

  private static final String DATABASE_NAME = "meetingsdb1.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_MEETINGS + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_GCM_REG_ID + " text not null, " + COLUMN_SUBJECT + " text not null, " + COLUMN_LOCATION  + " text not null, " +  COLUMN_DATE + " text not null, " + COLUMN_TIME + " text not null, " + COLUMN_INVITEE + " text not null );";

  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEETINGS);
    onCreate(db);
  }

} 
