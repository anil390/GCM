package com.anil.android.gcm;



public class Meeting {
	
	private int id;
	private String gcm_reg_id;
	private String location;
	private String subject;
	private String date;
	private String time;
	
	public Meeting(){
		
	}
public String getGcm_reg_id() {
		return gcm_reg_id;
	}
	public void setGcm_reg_id(String gcm_reg_id) {
		this.gcm_reg_id = gcm_reg_id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	/*	public Meeting(int id, String subject, String date) {
		super();
		this.id = id;
		this.subject = subject;
		this.date = date;
	}*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return date;
	   
	  }

}

