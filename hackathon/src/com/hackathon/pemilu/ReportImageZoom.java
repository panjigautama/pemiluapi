package com.hackathon.pemilu;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockActivity;
import com.squareup.picasso.Picasso;

@EActivity(R.layout.report_image_zoom)
public class ReportImageZoom extends SherlockActivity
{
	
	public static final String URL = "url";
	private String url;
	
	@ViewById
	ImageView image;
	
	@AfterViews
	void init()
	{
		Bundle bundle = getIntent().getExtras();
		url = bundle.getString( URL );
		Picasso.with(this).load( url ).into(image);
	}

}
