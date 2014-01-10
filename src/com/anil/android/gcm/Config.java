package com.anil.android.gcm;

public interface Config {

	static final String YOUR_SERVER_URL =  "http://www.abcd.co.in/gcmdemo/register.php";
	static final String UPLOAD_LOCATION = "http://www.abcd.co.in/gcmdemo/uploadlocation.php";
	static final String MEETING_CREATE_LOCATION = "http://www.abcd.co.in/gcmdemo/meetingcreation.php";

	
	 static final  String DB_PATH = "/data/data/com.anil.android.gcm/databases/";
	 
	 static final String DB_NAME = "gcm_demo.db";
    // Google project id
    static final String GOOGLE_SENDER_ID = "34232168699"; 

    static final String TAG = "GCM Android Example";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.anil.android.gcm.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";
		
	
}
