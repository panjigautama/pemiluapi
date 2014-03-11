package com.hackathon.pemilu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;

public class FacebookHelper 
{
	
	private FacebookHelperCallback mHelperCallback;
	private Bundle mSavedInstanceState;
	private Activity mActivity;
	private String[] mReadPermissions 		= { "basic_info","email" };
	private String[] mPublishPermissions 	= { "basic_info","email","publish_actions" };
	
	public FacebookHelper( Activity activity, Bundle savedInstanceState )
	{
		mActivity 	= activity;
		mSavedInstanceState = savedInstanceState;
        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        
        Session session = Session.getActiveSession();
        if (session == null) 
        {
            if ( mSavedInstanceState != null ) 
            {
                session = Session.restoreSession( mActivity, null, statusCallback, mSavedInstanceState);
            }
            if ( session == null ) 
            {
                session = new Session( mActivity );
            }
            Session.setActiveSession(session);
            if ( session.getState().equals( SessionState.CREATED_TOKEN_LOADED ) ) 
            {
                session.openForRead( new Session.OpenRequest( mActivity ).setCallback( statusCallback ) );
            }
        }
	}
	
	public void requestLogin( Activity activity ) 
	{
        Session session = Session.getActiveSession();
        if ( !session.isOpened() && !session.isClosed() ) 
        {
            session.openForRead(new Session.OpenRequest( activity ).setPermissions(mReadPermissions).setCallback( statusCallback ));
        } 
        else 
        {
            Session.openActiveSession( activity, true, statusCallback );
        }
    }

	public void requestLogout() 
	{
        Session session = Session.getActiveSession();
        if ( !session.isClosed() ) 
        {
            session.closeAndClearTokenInformation();
        }
    }
	
	/**
	 * set on request listener
	 * @param listener OnRequestListener
	 */
	public void setOnRequestListener( FacebookHelperCallback statusCallback )
	{
		mHelperCallback = statusCallback;
	}
	
	public interface FacebookHelperCallback
	{
		void onStatusCallback( Session session, SessionState state, Exception exception );
		void onRequestProfileCallback( Session previousSession, GraphUser user, Response response );
	}

    private Session.StatusCallback statusCallback = new SessionStatusCallback();

    private class SessionStatusCallback implements Session.StatusCallback 
    {
    	
        @Override
        public void call(Session session, SessionState state, Exception exception) 
        {
        	if( exception == null )
        	{
        		if( mHelperCallback != null )
            	{
            		mHelperCallback.onStatusCallback( session, state, exception );
            	}
            	
            	Session activeSession = Session.getActiveSession();
    	        if( activeSession.isOpened() )
    	        {
    	        	requestProfile(session);
    	        } 
        	}
	        else
	        {
	           Log.e( "FacebookHelper#onStatusCallback", exception.getMessage() );
	        }
        }
    }
    
    public void requestProfile( final Session session ) 
    {
       // Make an API call to get user data and define a new callback to handle the response.
        Request.newMeRequest( session, new Request.GraphUserCallback() 
        {
            @Override
            public void onCompleted(GraphUser user, Response response) 
            {
            	if( mHelperCallback != null )
            	{
            		mHelperCallback.onRequestProfileCallback( session, user, response);
            	}
            }
            
        }).executeAsync();
    } 
    
    public void requestSearchByHashtah( final Session session, String hashtag ) 
    {
       // Make an API call to get user data and define a new callback to handle the response.
        Request.newMeRequest( session, new Request.GraphUserCallback() 
        {
            @Override
            public void onCompleted(GraphUser user, Response response) 
            {
            	if( mHelperCallback != null )
            	{
            		mHelperCallback.onRequestProfileCallback( session, user, response);
            	}
            }
            
        }).executeAsync();
    } 
    
    public String getProfilePictureURL( int id )
    {
    	return "http://graph.facebook.com/"+id+"/picture";
    }
    
}
