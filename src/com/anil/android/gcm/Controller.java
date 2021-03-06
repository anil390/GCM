package com.anil.android.gcm;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import android.app.AlertDialog;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class Controller extends Application{
	Controller aController = this;
	
	@Override
	public boolean stopService(Intent name) {
		
		return super.stopService(name);
	}

	private  final int MAX_ATTEMPTS = 5;
    private  final int BACKOFF_MILLI_SECONDS = 2000;
    private  final Random random = new Random();
	
    void createMeeting(final Context context, String reg_Id,  String host, String location, String subject, String date, String time, String invitee){
    
    	Log.d("Controller ", reg_Id);
    	 Map<String, String> params = new HashMap<String, String>();
    	 params.put("invitee", invitee);
    	 params.put("time", time);
    	 params.put("date", date);
    	 params.put("subject", subject);
         params.put("location", location);
         params.put("host", host);
         params.put("regId", reg_Id);
         
        
         try {
        	 Log.d("meeting", "posting meeting on server");
			post("http://www.abcd.co.in/gcmdemo/meetingcreation.php", params);
		} catch (IOException e) {
			Log.d("posting", "catch block executed");
			e.printStackTrace();
		}
    }
    
	 // Register this account with the server.
    void register(final Context context, String mobile, String name, String email, final String regId) {
    	SharedPreferences sharedPreferences = PreferenceManager
    				                .getDefaultSharedPreferences(this);
    				        Editor editor = sharedPreferences.edit();
    				        editor.putString("Mobile", mobile);
    				        editor.putString("Email", email);
    				        editor.putString("Name", name);
    				        editor.putString("Registration_Id", regId);
    				        editor.putBoolean("is_registered", true);
    				        editor.commit();
        
       // String serverUrl = Config.YOUR_SERVER_URL;
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        params.put("regId", regId);
        params.put("name", name);
        params.put("email", email);
        
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        
        // Once GCM returns a registration id, we need to register on our server
        // As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
        	
            Log.d(Config.TAG, "Attempt #" + i + " to register");
            
            try {
            	//Send Broadcast to Show message on screen
            	displayMessageOnScreen(context, context.getString(
                        R.string.server_registering, i, MAX_ATTEMPTS));
                
                // Post registration values to web server
            	Log.d("Posting", "posting value on server started");
                post("http://www.abcd.co.in/gcmdemo/register.php", params);
                
                GCMRegistrar.setRegisteredOnServer(context, true);
              
                
                return;
            } catch (IOException e) {
            	
                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
            	
                Log.e(Config.TAG, "Failed to register on attempt " + i + ":" + e);
                
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                	
                    Log.d(Config.TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                    
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d(Config.TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return;
                }
               
                backoff *= 2;
            }
        }
        
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
        
        //Send Broadcast to Show message on screen
        displayMessageOnScreen(context, message);
    }

     // Unregister this account/device pair within the server.
     void unregister(final Context context, final String regId) {
    	 
        Log.i(Config.TAG, "unregistering device (regId = " + regId + ")");
        
        String serverUrl = Config.YOUR_SERVER_URL + "/unregister";
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        
        try {
            post(serverUrl, params);
            GCMRegistrar.setRegisteredOnServer(context, false);
            String message = context.getString(R.string.server_unregistered);
            displayMessageOnScreen(context, message);
        } catch (IOException e) {
        	
            // At this point the device is unregistered from GCM, but still
            // registered in the our server.
            // We could try to unregister again, but it is not necessary:
            // if the server tries to send a message to the device, it will get
            // a "NotRegistered" error message and should unregister the device.
        	
            String message = context.getString(R.string.server_unregister_error,
                    e.getMessage());
            displayMessageOnScreen(context, message);
        }
    }

    // Issue a POST request to the server.
    private static void post(String endpoint, Map<String, String> params)
            throws IOException {   	
        
        URL url;
        try {
        	Log.d("Url reached", " Post function started registering device ");
        	
            url = new URL(endpoint);
            
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator =  params.entrySet().iterator();
        //Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        
        String body = bodyBuilder.toString();
        
        Log.v(Config.TAG, "Posting '" + body + "' to " + url);
        
        byte[] bytes = body.getBytes();
        
        HttpURLConnection conn = null;
        try {
        
        	
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            
            // handle the response
            Log.d("Handlingresponse", "handling response device ");
            int status = conn.getResponseCode();
            String tmp = Integer.toString(status);
            Log.d("Handlingresponse", tmp);	
            // If response is not success
            if (status != 200) {
            	
              throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
      }
    
    
    
	// Checking for all possible internet providers
    public boolean isConnectingToInternet(){
    	
        ConnectivityManager connectivity = 
        	                 (ConnectivityManager) getSystemService(
        	                  Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
	
   // Notifies UI to display a message.
   void displayMessageOnScreen(Context context, String message) {
    	 
        Intent intent = new Intent(Config.DISPLAY_MESSAGE_ACTION);
        intent.putExtra(Config.EXTRA_MESSAGE, message);
        
        // Send Broadcast to Broadcast receiver with message
        context.sendBroadcast(intent);
        
    }
    
    
   //Function to display simple Alert Dialog
   
public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);

		if(status == false)
			
			alertDialog.setIcon(R.drawable.fail);
			alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
					
				
			}
		});
			AlertDialog dialog = alertDialog.create();
			dialog.show();
	}
 

	@Override
	public void onCreate() {
		super.onCreate();
		
		
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				Config.DISPLAY_MESSAGE_ACTION));
		Intent inte = new Intent(this, UploadingService.class);
		startService(inte);
		Log.d("service", "service started");
	}
	
	
	
	
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			String newMessage = intent.getExtras().getString(Config.EXTRA_MESSAGE);
			
			Toast.makeText(getApplicationContext(), "Got Message: " + newMessage, Toast.LENGTH_LONG).show();
			Intent in = new Intent(getApplicationContext(),RequestForMeeting.class);
			in.putExtra("message", newMessage);
			in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(in);
			
			// Releasing wake lock
			//aController.releaseWakeLock();
		}
	};
	
	
	
	protected void onDestroy() {
		
		try {
			
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
			
			
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
	
	}

    
   
}
