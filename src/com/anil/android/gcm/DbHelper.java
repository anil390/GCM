package com.anil.android.gcm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_NAME = "token";
    public static final String COLUMN_PHONE ="phone_number";
    public static final String COLUMN_NAME ="name";
    public static final String COLUMN_EMAIL ="email";
    public static final String COLUMN_REGID ="dev_reg_id";
    

    private static final String DATABASE_NAME = "gcm_demo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + " ( " + COLUMN_PHONE  + " integer primary key , " + COLUMN_NAME +" text not null, "+ COLUMN_EMAIL+" text not null, "+ COLUMN_REGID + " text);";
   // private static final String DATABASE_SELECT = "select * from token";
    public DbHelper(Context context) 
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) 
    {
    	Log.d("DbHelper", "data base helper class started" +DATABASE_CREATE);

        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
    }
    
    public Cursor fetchRegid()

    {
    	Log.d("fetching", "fetchRegiID called");
    	SQLiteDatabase db = this.getReadableDatabase();

              return db.query(TABLE_NAME, new String[] { COLUMN_PHONE,COLUMN_NAME, COLUMN_EMAIL,COLUMN_REGID}, null, null, null,null, null);                                                                        

    }
    
   
    
    

}