package com.anil.android.gcm;

public class ItemModel {
	
	private int id;
	private String subject;
	private String date;
	public ItemModel(int id, String subject, String date) {
		super();
		this.id = id;
		this.subject = subject;
		this.date = date;
	}
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
