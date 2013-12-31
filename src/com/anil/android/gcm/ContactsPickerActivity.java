package com.anil.android.gcm;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

public class ContactsPickerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String[] items = getlistcontacts();
		
		
	}
	public String[] getlistcontacts() {
	   

	    int i=0;
	    ContentResolver cr = getContentResolver();
	     Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null, null, null);
	     Cursor pCur = cr.query(
	                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,  null, null , null);
	        int a= cur.getCount();
	    String[] cttlist=new String[a+1];

	    cur.moveToFirst();
	    pCur.moveToFirst();
	    for (int j=0; j<a;j++){
	        int nm=cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
	        //int nb=pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
	        String name=cur.getString(nm);
	        int nb=pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

	        String number=pCur.getString(nb);
	        cttlist[i]=name.concat("<;>").concat(number);

	        //Toast.makeText(PizzastimeActivity.this, "alkamkljziha"+name+":"+number, Toast.LENGTH_LONG).show();
	        i++;
	        cur.moveToNext();
	        pCur.moveToNext();
	    }

	    return cttlist;
	}
	
	

}
