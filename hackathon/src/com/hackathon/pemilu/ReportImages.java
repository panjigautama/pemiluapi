package com.hackathon.pemilu;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.hackathon.pemilu.InstagramHelper.InstagramObject;
import com.hackathon.pemilu.InstagramHelper.OnRequestListener;

@EActivity(R.layout.report_images)
public class ReportImages extends SherlockActivity 
{
	
	@ViewById
	ListView list_reports;
	
	private static final String TAG = "Social Media Images";
	private static final int SETTING = 1;
	private static final int REFRESH = 2;
	
	private static SharedPreferences mSharedPreferences;
	
	private ReportImageAdapter imageAdapter;
	private List<ReportImage> reportImages;
	ProgressDialog progDialog;

	// twitter
	private static Twitter twitter;
	
	// instagram
	private static InstagramHelper instagram;
	
	// share
	private static int tempPhoto				= 0;
	private static final int PICK_FROM_CAMERA 	= 1;
	private static final int PICK_FROM_FILE 	= 2;
	public AlertDialog dialog ;
	private Uri mFileUri;
	private String mFilePath;
	
	@AfterViews
	void init()
	{
		mSharedPreferences = getSharedPreferences(SocmedHelper.PREFERENCE_NAME, MODE_PRIVATE);
		reportImages = new ArrayList();
		
		// actionbar setup
	    ActionBar bar = getSupportActionBar();
	    bar.setTitle("Mata Pemilu");
	    bar.setDisplayHomeAsUpEnabled(true);
	    
	    dialog = imagePicker().create();

	    instagram = new InstagramHelper( this, SocmedHelper.INSTA_TOKEN );
	    
	    reload();
	}
	
	@UiThread
	void showDialog( boolean state )
	{
		if( ( state == true ) && ( progDialog == null || progDialog.isShowing() == false ) )
		{
			progDialog = ProgressDialog.show( this, "", "fetch images .." );
			progDialog.show();
		}
		else
		{
			progDialog.dismiss();
		}
	}
	
	void reload()
	{
		if( progDialog == null || progDialog.isShowing() == false )
		{
			showDialog(true);
			
			reportImages.clear();
			
		    getQueryImagesFromTwitter();
		    getQueryImagesFromInstagram();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, SETTING, 0, "Setting").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		menu.add(0, REFRESH, 0, "Refresh").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case SETTING:
		        // App icon in Action Bar clicked; go up
		        Intent intent = new Intent(this, SettingSocmed_.class);
		        startActivity(intent);
		        return true;
		    case REFRESH:
		    	reload();
		    	return true;
		    case android.R.id.home:
				finish();
				return true;
		    default:
		        return super.onOptionsItemSelected(item);
	    }
	}
	
	@Background
	void getQueryImagesFromInstagram()
	{
		instagram.setOnRequestListener(new OnRequestListener() {
			

			@Override
			public void onRequestComplete(List<InstagramObject> objects) {
				if( objects != null )
				{
					for( int i =0; i < objects.size(); i++ )
					{
						InstagramObject object = objects.get(i);
						Date createdAt 		= new Date(Long.parseLong(object.getCreated_time())* 1000);
						
						String username 	= object.getUser().getUsername();
						String image_url 	= object.getImages().getStandard_resolution().getUrl();
						String caption 		= object.getCaption().getText();
						
						if( username == null )
						{
							Log.i("", "username" );
						}
						if( image_url == null )
						{
							Log.i("", "image_url" );
						}
						if( caption == null )
						{
							Log.i("", "caption" );
						}
						if( createdAt == null )
						{
							Log.i("", "createdAt" );
						}
						
						ReportImage item 	= new ReportImage( username, image_url, caption, createdAt, ReportImage.INSTAGRAM );
						reportImages.add(item);
					}
					
					notifyAdapter();
				}
			}
		});
		instagram.searchByHashtag("matapemilu2014");
	}

	@Background
	void getQueryImagesFromTwitter()
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
		
		// query
		Query query = new Query("#matapemilu2014 filter:images");
		try 
		{		    
			QueryResult result = twitter.search(query);
 			List<Status> status = result.getTweets();			
			setAdapter( status );
		}   
		catch (TwitterException e) 
		{
			e.printStackTrace();
		}
	}
	
	@UiThread
	void setAdapter( List<Status> status )
	{
		for( int i =0; i < status.size(); i++ )
		{
			Status itemStatus = status.get(i);
			MediaEntity[] media = itemStatus.getMediaEntities();
			String url = "";
			if( media.length > 0 ) {
				url = media[0].getMediaURL();
			}
			ReportImage item = new ReportImage( itemStatus.getUser().getName(), url, itemStatus.getText(), itemStatus.getCreatedAt(), ReportImage.TWITTER );
			reportImages.add(item);
			
			notifyAdapter();
		}
		
    }
	
	@UiThread
	void notifyAdapter()
	{
		
		if( reportImages.size() > 0 )
		{
			// desc sort
			Collections.sort(reportImages, new Comparator<ReportImage>() {
			    public int compare(ReportImage m1, ReportImage m2) {
			        return m2.getDate().compareTo(m1.getDate());
			    }
			});
		}
		
		if( imageAdapter == null )
		{
			imageAdapter = new ReportImageAdapter( this, R.layout.report_image_item, reportImages );
		}
		else
		{
			imageAdapter.notifyDataSetChanged();
		}
		list_reports.setAdapter(imageAdapter);	
		list_reports.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				ReportImage reportImage = reportImages.get( position );
				Intent i = new Intent( ReportImages.this, ReportImageZoom_.class );
				i.putExtra( ReportImageZoom.URL, reportImage.getImageLink() );
				startActivity(i);
			}
			
		});
		
		showDialog( false );
	}
	
	@Click(R.id.report_button)
	void shareButton()
	{
		if( dialog != null && dialog.isShowing() == false )
		{
			dialog.show();
		}
	}

	// share
	public Builder imagePicker() 
	{
		//image picker
		final String [] items           = new String [] {"From Camera", "From SD Card"};
		ArrayAdapter<String> adapter  	= new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
		AlertDialog.Builder builder     = new AlertDialog.Builder(this);

		builder.setTitle("Select Image");
		return builder.setAdapter( adapter, new DialogInterface.OnClickListener() 
		{
			public void onClick( DialogInterface dialog, int item ) 
			{
				if (item == 0) 
				{

					Intent takePicture 	= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					mFileUri 			= MediaHelper.getOutputMediaFileUri(MediaHelper.MEDIA_TYPE_IMAGE);
					if( mFileUri == null )
					{
						Toast.makeText( ReportImages.this, "No external storage available to save image from camera", Toast.LENGTH_LONG ).show();
						return;
					}

					takePicture.putExtra( MediaStore.EXTRA_OUTPUT, mFileUri );

					// start intent
					startActivityForResult(takePicture, PICK_FROM_CAMERA);

					dialog.cancel();
				} 
				else 
				{
					Intent pickPhoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(pickPhoto , PICK_FROM_FILE);
				}
			}
		} );
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (resultCode != RESULT_OK) 
		{
			return;
		}

		File file;
		if( data != null )
		{
			Uri selectedImage 	= data.getData();
			mFilePath 			= MediaHelper.getFilenameByUri( ReportImages.this, selectedImage );
		}
		else
		{
			mFilePath = mFileUri.getPath();
		}

		// pass filepath to another activity
		Intent i = new Intent( this, ReportImageShare_.class );
		i.putExtra( ReportImageShare.FILEPATH, mFilePath );
		startActivity(i);
		
	}

}
