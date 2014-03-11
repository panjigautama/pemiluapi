package com.hackathon.pemilu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class InstagramHelper 
{
	
	private AsyncHttpClient mClient;
	private Activity activity;
	private OnRequestListener mListener;
	private String access_token;
	
	public InstagramHelper( Activity activity, String access_token )
	{
		this.mClient 		= new AsyncHttpClient();
		this.activity 		= activity;
		this.access_token 	= access_token;
	}
	
	public void setOnRequestListener( OnRequestListener listener )
	{
		mListener = listener;
	}
	
	public interface OnRequestListener
	{
		void onRequestComplete( List<InstagramObject> objects );
	}
	
	void searchByHashtag( String hashtag )
	{
		mClient.get( "https://api.instagram.com/v1/tags/"+hashtag+"/media/recent?access_token="+access_token, responseSearchHandler);
	}
	
	private AsyncHttpResponseHandler responseSearchHandler = new AsyncHttpResponseHandler()
	{
		@Override
		public void onFailure(Throwable throwable, String response) 
		{
			if( mListener != null )
			{
				mListener.onRequestComplete( null );
			}
		}
		
		@Override
		public void onSuccess(int statusCode, String response) 
		{
			try {
				JSONObject instagramResponse = new JSONObject(response);
				JSONArray mediaArray = instagramResponse.getJSONArray("data");
				List<InstagramObject> instagramObjects = new ArrayList();
				Gson gson = new GsonBuilder().create();

				for( int i =0; i < mediaArray.length(); i++ )
				{
					String objectString = mediaArray.get(i).toString();
		            InstagramObject object = gson.fromJson( objectString, InstagramObject.class);
		            instagramObjects.add( object );
				}
				
				if( mListener != null && instagramObjects != null )
				{
					mListener.onRequestComplete(instagramObjects);
				}
								
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	};
	
	public class InstagramLocation
	{
		double latitude;
		double longitude;
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
		
	}
	
	public class InstagramImage
	{
		String url;
		int width;
		int height;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		
	}
	
	public class InstagramImages
	{
		InstagramImage low_resolution;
		InstagramImage thumbnail;
		InstagramImage standard_resolution;
		public InstagramImage getLow_resolution() {
			return low_resolution;
		}
		public void setLow_resolution(InstagramImage low_resolution) {
			this.low_resolution = low_resolution;
		}
		public InstagramImage getThumbnail() {
			return thumbnail;
		}
		public void setThumbnail(InstagramImage thumbnail) {
			this.thumbnail = thumbnail;
		}
		public InstagramImage getStandard_resolution() {
			return standard_resolution;
		}
		public void setStandard_resolution(InstagramImage standard_resolution) {
			this.standard_resolution = standard_resolution;
		}
		
	}
	
	public class InstagramCaption
	{
		String created_time;
		String text;
		String id;
		
		public String getCreated_time() {
			return created_time;
		}
		public void setCreated_time(String created_time) {
			this.created_time = created_time;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
	}
	
	public class InstagramUser
	{
		String username;
		String website;
		String profile_picture;
		String full_name;
		String bio;
		String id;
		
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getWebsite() {
			return website;
		}
		public void setWebsite(String website) {
			this.website = website;
		}
		public String getProfile_picture() {
			return profile_picture;
		}
		public void setProfile_picture(String profile_picture) {
			this.profile_picture = profile_picture;
		}
		public String getFull_name() {
			return full_name;
		}
		public void setFull_name(String full_name) {
			this.full_name = full_name;
		}
		public String getBio() {
			return bio;
		}
		public void setBio(String bio) {
			this.bio = bio;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
	}

	public class InstagramObject
	{
		
		String type;
		String filter;
		String created_time;
		String link;
		String id;
		String[] tags;
		boolean user_has_liked;
		InstagramImages images;
		InstagramCaption caption;
		InstagramUser user;
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getFilter() {
			return filter;
		}
		public void setFilter(String filter) {
			this.filter = filter;
		}
		public String getCreated_time() {
			return created_time;
		}
		public void setCreated_time(String created_time) {
			this.created_time = created_time;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String[] getTags() {
			return tags;
		}
		public void setTags(String[] tags) {
			this.tags = tags;
		}
		public boolean isUser_has_liked() {
			return user_has_liked;
		}
		public void setUser_has_liked(boolean user_has_liked) {
			this.user_has_liked = user_has_liked;
		}
		public InstagramImages getImages() {
			return images;
		}
		public void setImages(InstagramImages images) {
			this.images = images;
		}
		public InstagramCaption getCaption() {
			return caption;
		}
		public void setCaption(InstagramCaption caption) {
			this.caption = caption;
		}
		public InstagramUser getUser() {
			return user;
		}
		public void setUser(InstagramUser user) {
			this.user = user;
		}
	}
	
}
