package com.hackathon.pemilu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockFragment;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DPRFragment extends SherlockFragment {

	private GridView gridView;
	private ArrayList<Party> partyList;
	private ImageAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dpr, null);
		gridView = (GridView) view.findViewById(R.id.grid_view);
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
							adapter = new ImageAdapter(getSherlockActivity(),
									partyList);
							gridView.setAdapter(adapter);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});

		return view;
	}
}
