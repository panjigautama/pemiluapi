package com.hackathon.pemilu;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

	private static final String PREF_KEY_PROVINCE_ID = "province_id";
	private static final String PREF_KEY_DAPIL_ID_DPR = "dapil_dpr_id";
	private static final String PREF_KEY_DAPIL_ID_DPRD = "dapil_dprd_id";
	private static final String PREF_KEY_DAPIL_NAME_DPRD = "dapil_dprd_name";
	private static final String PREF_KEY_PARTY_ID = "party_id";
	private static final String PREF_KEY_DISTRICT = "district_id";

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

	public String getDapilDPRD() {
		return prefs.getString(PREF_KEY_DAPIL_ID_DPRD, null);
	}

	public void setDapilDPRD(String id, String lembaga) {
		editor.putString(PREF_KEY_DAPIL_ID_DPRD, id);
		editor.putString(PREF_KEY_DAPIL_NAME_DPRD, lembaga);
		editor.commit();
	}

	public String getDapilDPR() {
		return prefs.getString(PREF_KEY_DAPIL_ID_DPR, null);
	}

	public void setDapilDPR(String id) {
		editor.putString(PREF_KEY_DAPIL_ID_DPR, id);
		editor.commit();
	}

	public String getDistrict() {
		return prefs.getString(PREF_KEY_DISTRICT, null);
	}

	public void setDistrict(String district) {
		editor.putString(PREF_KEY_DISTRICT, district);
		editor.commit();
	}
}
