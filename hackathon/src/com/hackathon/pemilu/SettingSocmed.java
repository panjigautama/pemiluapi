package com.hackathon.pemilu;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.dina.oauth.instagram.InstagramApp;
import br.com.dina.oauth.instagram.InstagramApp.OAuthAuthenticationListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.hackathon.pemilu.FacebookHelper.FacebookHelperCallback;

@EActivity(R.layout.setting_socialmedia)
public class SettingSocmed extends SherlockActivity  {
	
	private static final String TAG = "SocialMedia Setting";
	
	FacebookHelper fbHelper;
	
	@ViewById
	ImageView connect_twitter;
	
	@ViewById
	ImageView connect_facebook;
	
//	@ViewById
//	Button connect_instagram;
	
	// twitter
	private static Twitter twitter;
	private static RequestToken requestToken;
	private static SharedPreferences mSharedPreferences;
	
	// instagram
	private InstagramApp mApp;
	
	@AfterViews
	void init()
	{
		mSharedPreferences = getSharedPreferences(SocmedHelper.PREFERENCE_NAME, MODE_PRIVATE);
		checkTwitterOAuth();
		
	    ActionBar bar = getSupportActionBar();
	    bar.setDisplayHomeAsUpEnabled(true);

		// instagram init
		if( mApp == null )
		{
			mApp = new InstagramApp(this, SocmedHelper.INSTA_CLIENT_ID, SocmedHelper.INSTA_CLIENT_SECRET, SocmedHelper.INSTA_CALLBACK_URL);
			mApp.setListener(listener);
		}
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
	void facebookConnected()
	{
		connect_facebook.setImageResource(R.drawable.facebook_connect);
	}
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// initiate helper
		if( fbHelper == null )
		{
			fbHelper = new FacebookHelper( this, savedInstanceState );
			fbHelper.setOnRequestListener(new FacebookHelperCallback() {
				
				@Override
				public void onStatusCallback( Session session, SessionState state, Exception exception) 
				{
			        if( exception != null )
			        {
			           Toast.makeText( SettingSocmed.this, exception.getMessage(), Toast.LENGTH_SHORT ).show();
			        }
				}
				
				@Override
				public void onRequestProfileCallback( Session previousSession, GraphUser user, Response response ) 
				{
					// If the response is successful
		            if ( previousSession == Session.getActiveSession() ) 
		            {
		                if (user != null) 
		                {
                            String id 			= user.getId();
                            String email 		= user.asMap().get("email").toString();
                            String first_name 	= user.getFirstName();
                            String last_name 	= user.getLastName();
                            
                            facebookConnected();
                            
		    				Toast.makeText( SettingSocmed.this, "Facebook : "+first_name+", email : "+email, Toast.LENGTH_SHORT ).show();
		                }
		            }
		            if (response.getError() != null) 
		            {
		                // Handle errors, will do so later.
		            }
				}
			});
			
			Session activeSession = Session.getActiveSession();
	        if( activeSession.isOpened() )
	        {
	        	facebookConnected();
	        } 
		}

	};
	
	OAuthAuthenticationListener listener = new OAuthAuthenticationListener() {

		@Override
		public void onSuccess() 
		{
			Toast.makeText( SettingSocmed.this, "Connected as : "+mApp.getUserName(), Toast.LENGTH_SHORT).show();
//			connect_instagram.setText("Disconnect");
		}

		@Override
		public void onFail(String error) 
		{
			Toast.makeText( SettingSocmed.this, error, Toast.LENGTH_SHORT).show();
		}
		
	};

//	@Click(R.id.connect_instagram)
//	void connectInstagram()
//	{
//		Toast.makeText(this, "connect_instagram", Toast.LENGTH_SHORT).show();
//		askInstaOAuth();
//	}
	
	@Click(R.id.connect_facebook)
	void connectxFacebook()
	{
		fbHelper.requestLogin( this );
	}
	
	@Click(R.id.connect_twitter)
	void connectTwitter()
	{
		Toast.makeText(this, "connect_twitter", Toast.LENGTH_SHORT).show();
		if (isConnected()) {
			String token = mSharedPreferences.getString(SocmedHelper.PREF_KEY_TOKEN, null); 
			Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
		} else {
			askOAuth();
		}
	}
	
	@UiThread
	void setConnectedStatus()
	{
		connect_twitter.setImageResource(R.drawable.twitter_connect);
//		connect_twitter.setText( "twitter connected" );
	}
	
	@Background
	void checkTwitterOAuth()
	{
		if( twitter == null ){
			ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
			configurationBuilder.setOAuthConsumerKey(SocmedHelper.CONSUMER_KEY);
			configurationBuilder.setOAuthConsumerSecret(SocmedHelper.CONSUMER_SECRET);
			Configuration configuration = configurationBuilder.build();
			twitter = new TwitterFactory(configuration).getInstance();
		}
		
		if( isConnected() )
		{
			setConnectedStatus();
			return;
		}

		// handle twitter oAuth callback
		Uri uri = getIntent().getData();
		if (uri != null && uri.toString().startsWith(SocmedHelper.CALLBACK_URL)) {
			String verifier = uri.getQueryParameter(SocmedHelper.IEXTRA_OAUTH_VERIFIER);
            try
            { 
                AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier); 
                Editor e = mSharedPreferences.edit();
                e.putString(SocmedHelper.PREF_KEY_TOKEN, accessToken.getToken()); 
                e.putString(SocmedHelper.PREF_KEY_SECRET, accessToken.getTokenSecret()); 
                e.commit();
                
                setConnectedStatus();
	        } 
            catch (Exception e) 
	        { 
	            Log.e(TAG, e.getMessage()); 
			}
        }
	}
	
	@Background
	void askOAuth() 
	{
		try 
		{
			requestToken = twitter.getOAuthRequestToken(SocmedHelper.CALLBACK_URL);
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL()));
			i.setFlags( i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY );
			this.startActivity( i );
		} 
		catch (TwitterException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * check if the account is authorized
	 * @return
	 */
	private boolean isConnected() 
	{
		return mSharedPreferences.getString(SocmedHelper.PREF_KEY_TOKEN, null) != null;
	}
	
	private void disconnectTwitter() {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.remove(SocmedHelper.PREF_KEY_TOKEN);
		editor.remove(SocmedHelper.PREF_KEY_SECRET);
		editor.commit();
	}
	
	// INSTAGRAM
	void askInstaOAuth()
	{
		if ( mApp.hasAccessToken() == false ) 
		{
			mApp.authorize();
		}
		else
		{
			String token = mApp.getToken();
			Toast.makeText( this, mApp.getToken(), Toast.LENGTH_LONG ).show();
		}
	}
	
	// facebook
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		// facebook login
        Session activeSession = Session.getActiveSession();
        if( activeSession != null )
        {
        	activeSession.onActivityResult( this, requestCode, resultCode, data );
        }
	}
	
}
