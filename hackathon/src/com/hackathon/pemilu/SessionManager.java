package com.hackathon.pemilu;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

	private static final String PREF_KEY_PROVINCE_ID = "province_id";
	private static final String PREF_KEY_PARTY_ID = "party_id";

	private Context context;
	private SharedPreferences prefs;
	private Editor editor;

	public SessionManager(Context context) {
		this.context = context;
		this.prefs = this.context.getSharedPreferences("options", 0);
		this.editor = prefs.edit();
	}

	public int getProvinceId() {
		return prefs.getInt(PREF_KEY_PROVINCE_ID, 0);
	}

	public void setProvinceId(int id) {
		editor.putInt(PREF_KEY_PROVINCE_ID, id);
		editor.commit();
	}

	public int getPartyId() {
		return prefs.getInt(PREF_KEY_PARTY_ID, 0);
	}

	public void setPartyId(int id) {
		editor.putInt(PREF_KEY_PARTY_ID, id);
		editor.commit();
	}
}
