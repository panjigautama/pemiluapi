package com.hackathon.pemilu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class MediaHelper 
{

	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	
	public static String getFilenameByUri( Activity activity, Uri fileUri )
	{
		String path = "";
	    Uri filePathUri = fileUri;
	    if ( fileUri.getScheme() != null && fileUri.getScheme().toString().compareTo("content") == 0 )
	    {      
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = activity.getContentResolver().query( fileUri, filePathColumn, null, null, null );
			try
			{
				if (cursor != null && cursor.getCount() != 0)
				{
					cursor.moveToFirst();
					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					path 			= cursor.getString(columnIndex);
					cursor.close();
				}
			}
			catch( Exception e ) 
			{
				Log.e("Path Error",e.toString());
			}
			finally 
			{
				if( cursor != null ) 
				{
					cursor.close();
		    	}
		    }
	    }
	    else if ( fileUri.getScheme() != null && fileUri.getScheme().compareTo("file") == 0 )
	    {
	    	path = filePathUri.getLastPathSegment().toString();
	    }
	    else
	    {
	    	path = path+"_"+filePathUri.getLastPathSegment();
	    }
		return path;
	}
	
	/** Create a file Uri for saving an image or video */
	public static Uri getOutputMediaFileUri(int type)
	{
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	public static File getOutputMediaFile(int type)
	{
		File mediaStorageDir = null;
		
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) 
		{
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		    
		    // This location works best if you want the created images to be shared
		    // between applications and persist after your app has been uninstalled.
		    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) 
		    {
 		        // only for gingerbread and newer versions
		    }
		    mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ), "Join" );
		} 
		else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) 
		{
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} 
		else 
		{
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		
		// check whether can write to external storage
		if( mediaStorageDir == null )
		{
			return null;
		}

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists() )
	    {
	        if (! mediaStorageDir.mkdirs())
	        {
	            Log.e("Join", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE)
	    {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
	    } 
	    else if(type == MEDIA_TYPE_VIDEO) 
	    {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_"+ timeStamp + ".mp4");
	    } 
	    else 
	    {
	        return null;
	    }

	    return mediaFile;
	}
	
}
