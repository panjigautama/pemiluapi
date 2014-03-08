package com.hackathon.pemilu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SelectPartyActivity extends SherlockActivity {

	GridView gridView;
	ArrayList<Party> partyList;
	ImageAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_party);

		gridView = (GridView) findViewById(R.id.grid_view);
		partyList = new ArrayList<Party>();

		RequestParams params = new RequestParams();
		params.put("apiKey", Constants.PEMILUAPI_KEY);
		HackathonRESTClient.get("/candidate/api/partai", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						try {
							JSONArray partyArray = response
									.getJSONObject("data")
									.getJSONObject("results")
									.getJSONArray("partai");
							for (int i = 0; i < partyArray.length(); i++) {
								JSONObject partyObject = partyArray
										.getJSONObject(i);
								Party party = new Party(partyObject
										.getString("nama"), partyObject
										.getString("nama_lengkap"), partyObject
										.getInt("id"), partyObject
										.getString("url_logo_medium"));
								partyList.add(party);
							}
							adapter = new ImageAdapter(
									SelectPartyActivity.this, partyList);
							gridView.setAdapter(adapter);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

}
