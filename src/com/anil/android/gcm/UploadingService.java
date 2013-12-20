package com.anil.android.gcm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class UploadingService extends Service implements LocationListener {
	 private LocationManager locationManager;
	  private String provider;
	  //private String regUrl;
	@Override
	public void onCreate() {
		//Log.d("provider", "null provider");	
		super.onCreate();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Log.d("provider", "null provider"+provider);
	    Location location = locationManager.getLastKnownLocation(provider);
	    if (location != null) {
		    Log.d("UploadingService", "before onLocationCheanged");
		      onLocationChanged(location);
	    }
	    else
	    {
	    	
	    	 locationManager.requestLocationUpdates(provider, 400, 1, this);
	       
	    	Log.d("not found", "NO Location Found");
	    	//location.setLatitude(37.422006)	;
	    	//location.setLongitude(-122.084095);
	    	//onLocationChanged(location);
	    	
	    	
	    }
	}
	    @Override
		  public void onLocationChanged(Location location) {
	      	Log.d("Uploading Service", "onLocationChanged called");
	    	
		    float lat = (float) (location.getLatitude());
		    String tempLat = Float.toString(lat);
		    float lng = (float) (location.getLongitude());
		    String tempLong = Float.toString(lng);
		    new UploadAsyncTask().execute("APA91bHcQrgtm3AnyYB3eBH8lssAJ7Imy4BJlCoApQXKW0Av0Wh9Ddc6-zYchuduhsdbKEErotpI75vmvetuiz_NfGgr34TRvuWbYlpD8V6Kl4DO0vNJU7Cux6L7tYQV1bQ-5RJI4X1aeEy32IAynuYNiBOatBjW_A", tempLat, tempLong);
		    
		   /* HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(Config.UPLOAD_LOCATION);
		    try
		    {
		    	
		    	
		    	List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		    	// add an HTTP variable and value pair
*/		    /*	nameValuePairs.add(new BasicNameValuePair("gcm_reg_id", "APA91bHcQrgtm3AnyYB3eBH8lssAJ7Imy4BJlCoApQXKW0Av0Wh9Ddc6-zYchuduhsdbKEErotpI75vmvetuiz_NfGgr34TRvuWbYlpD8V6Kl4DO0vNJU7Cux6L7tYQV1bQ-5RJI4X1aeEy32IAynuYNiBOatBjW_A"));
		    	nameValuePairs.add(new BasicNameValuePair("lattitude", tempLat) );
		    	nameValuePairs.add(new BasicNameValuePair("longitude", tempLong) );
		    	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));*/
		    	// send the variable and value, in other words post, to the URL
		    	//HttpResponse response = httpclient.execute(httppost);
		    	//Log.d("Uploaded Service", "data uploaded"+tempLat);
		    	
		    	//URL url = new URL(Config.UPLOAD_LOCATION);
		    /*	//URLConnection conn = url.openConnection();
		    }
		    catch(Exception e){
		    	
		    	
		    }
		    finally{
		    	
		    }*/
		   
		  }

	@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
		
			return super.onStartCommand(intent, flags, startId);
			
		}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	 @Override
	  public void onStatusChanged(String provider, int status, Bundle extras) {
	   

	  }

	  @Override
	  public void onProviderEnabled(String provider) {
	    Toast.makeText(this, "Enabled new provider " + provider,
	        Toast.LENGTH_SHORT).show();

	  }

	  @Override
	  public void onProviderDisabled(String provider) {
	    Toast.makeText(this, "Disabled provider " + provider,
	        Toast.LENGTH_SHORT).show();
	  }
	  
	  private class UploadAsyncTask extends AsyncTask<String, Void, Void>{
		  @Override
	        protected Void doInBackground(String... params){
			  
			  Log.d("doInBAckground", "DO in Background called");
			  List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		    	// add an HTTP variable and value pair
		    	nameValuePairs.add(new BasicNameValuePair("gcm_reg_id", params[0]));
		    	nameValuePairs.add(new BasicNameValuePair("lattitude", params[1]) );
		    	nameValuePairs.add(new BasicNameValuePair("longitude", params[2]) );
		    	try {
		    	HttpClient httpclient = new DefaultHttpClient();
		    	HttpPost httppost = new HttpPost(Config.UPLOAD_LOCATION);
		    	
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,("utf-8")));
					try {
						Log.d("entered", "in http post");
						HttpResponse response = httpclient.execute(httppost);
						Log.d("postData", response.getStatusLine().toString());
					} catch (ClientProtocolException e) {
						Log.d("error", "in http post1");
					} catch (IOException e) {
						Log.d("error", "in http post2");
					}
				} catch (UnsupportedEncodingException e) {
					Log.d("error", "in http post3");
				}
	        	
			    
			  
	            return null;
	        }

		@Override
		protected void onPostExecute(Void result) {
			
			super.onPostExecute(result);
			Log.d("onPostExecute", "uploaded successfully");
		}

			
			
	    }  
	  
}
