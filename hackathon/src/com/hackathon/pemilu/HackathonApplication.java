package com.hackathon.pemilu;

import java.util.ArrayList;

import android.app.Application;

public class HackathonApplication extends Application {

	static HackathonApplication instance;
	SessionManager session;
	ArrayList<Area> areaList;

	public SessionManager getSession() {
		if (session == null) {
			session = new SessionManager(getApplicationContext());
		}
		return session;
	}

	public void setSession(SessionManager session) {
		this.session = session;
	}

	public ArrayList<Area> getAreaList() {
		if (areaList == null) {
			areaList = new ArrayList<Area>();
		}
		return areaList;
	}

	public void setAreaList(ArrayList<Area> areaList) {
		this.areaList = areaList;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

}
