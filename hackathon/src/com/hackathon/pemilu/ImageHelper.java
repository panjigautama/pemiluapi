/* Copyright (C) 2013 Gongsin.ltd All Rights Reserved

This file is part of Join(friendipity) project.

 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential

 * Written by Ahmad, Song, Panji, Wisnu, Geral <join.dev.git@gmail.com>, November 2013
 */

package com.hackathon.pemilu;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

public class ImageHelper 
{
	
	public static final String TAG = "ImageHelper";
	
	public static Bitmap decodeScaledBitmapFromSdCard(String filePath, int reqWidth, int reqHeight) 
	{
	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(filePath, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    
	    Bitmap decodedBitmap = BitmapFactory.decodeFile(filePath, options);
	    
	    // check exif rotation
		ExifInterface ei = null;
		try 
		{
			ei = new ExifInterface( filePath );
		} 
		catch (IOException e) 
		{
			Log.e( "Exif", e.getMessage() );
		}
		int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
		switch(orientation) 
		{
		    case ExifInterface.ORIENTATION_ROTATE_90:
		    	decodedBitmap = ImageHelper.rotateBitmap(decodedBitmap, 90);
		        break;
		    case ExifInterface.ORIENTATION_ROTATE_180:
		    	decodedBitmap = ImageHelper.rotateBitmap(decodedBitmap, 180);
		        break;
		}

	    return decodedBitmap;
	}
	
	public static Bitmap rotateBitmap( Bitmap image, int rotation )
	{
		Matrix matrix = new Matrix();
		matrix.postRotate( rotation );
		image = Bitmap.createBitmap(image, 0, 0,  image.getWidth(), image.getHeight(), matrix, true);
		return image;
	}

	public static int calculateInSampleSize( BitmapFactory.Options options, int reqWidth, int reqHeight) 
	{
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {

	        // Calculate ratios of height and width to requested height and width
	        final int heightRatio = Math.round((float) height / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);

	        // Choose the smallest ratio as inSampleSize value, this will guarantee
	        // a final image with both dimensions larger than or equal to the
	        // requested height and width.
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }

	    return inSampleSize;
	}	
}
