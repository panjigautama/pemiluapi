package com.hackathon.pemilu;

import java.util.Date;

public class ReportImage 
{
	
	String username;
	String imageLink;
	String caption;
	Date date;
	int type;
	
	public static final int TWITTER 	= 0;
	public static final int INSTAGRAM 	= 1;
	public static final int FACEBOOK 	= 2;
		
	public ReportImage(String username, String imageLink, String caption,
			Date date, int type) {
		super();
		this.username = username;
		this.imageLink = imageLink;
		this.caption = caption;
		this.date = date;
		this.type = type;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
