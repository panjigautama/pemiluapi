package com.hackathon.pemilu;

import android.app.Application;

public class HackathonApplication extends Application {

	static HackathonApplication instance;
	SessionManager session;

	public SessionManager getSession() {
		if (session == null) {
			session = new SessionManager(getApplicationContext());
		}
		return session;
	}

	public void setSession(SessionManager session) {
		this.session = session;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

}
