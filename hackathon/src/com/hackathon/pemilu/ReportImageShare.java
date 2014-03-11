package com.hackathon.pemilu;

import java.io.File;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

@EActivity(R.layout.report_image_share)
public class ReportImageShare extends SherlockActivity {
	
	public static final String FILEPATH = "filepath";
	private String mFilepath;
	private File mFile;
	private static Twitter twitter;
	private static SharedPreferences mSharedPreferences;
	
	ProgressDialog progDialog;

	@ViewById
	ImageView share_image;
	
	@ViewById
	Button share_button;
	
	@ViewById
	EditText share_message;
	
	@AfterViews
	void init()
	{
		mSharedPreferences = getSharedPreferences(SocmedHelper.PREFERENCE_NAME, MODE_PRIVATE);
		
		// actionbar setup
	    ActionBar bar = getSupportActionBar();
	    bar.setDisplayHomeAsUpEnabled(true);
	    bar.setTitle("Share Image");

		Bundle bundle = getIntent().getExtras();
		mFilepath = bundle.getString(FILEPATH);
		mFile = new File( mFilepath );

		Bitmap photo = ImageHelper.decodeScaledBitmapFromSdCard( mFile.getAbsolutePath(), 1024, 768 );
		share_image.setImageBitmap( photo );
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case android.R.id.home:
				finish();
				return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
	}
	
	@UiThread
	void showDialog(boolean state)
	{
		if( progDialog == null || progDialog.isShowing() == false )
		{
			progDialog = ProgressDialog.show( this, "", "share image .." );
			progDialog.setCancelable(false);
			progDialog.show();
		}
		else
		{
			progDialog.dismiss();
		}
	}
	
	@Click(R.id.share_button)
	void share()
	{
		shareTwitter();
	}
	
	@Background
	void shareTwitter()
	{
		// check whether twitter is connected
		boolean isTwitterConnected =  mSharedPreferences.getString(SocmedHelper.PREF_KEY_TOKEN, null) != null;
		if( isTwitterConnected == false )
		{
			return;
		}
		
		// only execute if twitter is connected
		if( twitter == null )
		{
			ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
			configurationBuilder.setOAuthConsumerKey(SocmedHelper.CONSUMER_KEY);
			configurationBuilder.setOAuthConsumerSecret(SocmedHelper.CONSUMER_SECRET);
			configurationBuilder.setOAuthAccessToken(  mSharedPreferences.getString(SocmedHelper.PREF_KEY_TOKEN, null) );
			configurationBuilder.setOAuthAccessTokenSecret( mSharedPreferences.getString(SocmedHelper.PREF_KEY_SECRET, null));
			Configuration configuration = configurationBuilder.build();
			twitter = new TwitterFactory(configuration).getInstance();
		}
		
		try{
			showDialog(true);
			String message = share_message.getEditableText().toString();
	        StatusUpdate status = new StatusUpdate( message + " #matapemilu2014" );
	        status.setMedia(mFile);
	        Status statusResult = twitter.updateStatus(status);
	        showDialog(false);
	        if( statusResult != null )
	        {
	        	shareSuccess();
	        }
	    }
	    catch(TwitterException e){
	        Log.d("TAG", "Pic Upload error" + e.getErrorMessage());
	    }
	}
	
	void shareFacebook()
	{
		
	}
	
	void shareInstagram()
	{
		
	}
	
	@UiThread
	void shareSuccess()
	{
		Toast.makeText( this, "Share image success", Toast.LENGTH_SHORT ).show();
		finish();
	}

}
