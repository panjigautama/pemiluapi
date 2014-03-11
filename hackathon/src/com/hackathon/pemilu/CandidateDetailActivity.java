package com.hackathon.pemilu;

import java.util.ArrayList;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

public class CandidateDetailActivity extends SherlockActivity {

	ListView detailListView;
	ArrayList<CandidateDetail> detailList;
	ImageView candidatePhoto;
	CandidateDetailAdapter adapter;
	HackathonApplication application;
	SessionManager session;

	String candidateId, photoUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_candidate_detail);
		application = (HackathonApplication) getApplicationContext();
		session = application.getSession();

		detailListView = (ListView) findViewById(R.id.detail_listview);
		detailList = new ArrayList<CandidateDetail>();
		candidatePhoto = (ImageView) findViewById(R.id.candidate_photo);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			candidateId = bundle.getString("candidate_id");
		}
		System.out.println(candidateId);
		RequestParams params = new RequestParams();
		params.put("apiKey", Constants.PEMILUAPI_KEY);

		HackathonRESTClient.get("/candidate/api/caleg/" + candidateId, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						JSONObject candidate;
						try {
							candidate = response.getJSONObject("data")
									.getJSONObject("results")
									.getJSONArray("caleg").getJSONObject(0);
							JSONArray keys = candidate.names();
							for (int i = 0; i < keys.length(); i++) {
								String key = keys.getString(i);
								String value = candidate.getString(key);
								if (key.equals("provinsi"))
									value = candidate.getJSONObject(key)
											.getString("nama");
								if (key.equals("dapil"))
									value = candidate.getJSONObject(key)
											.getString("nama");
								if (key.equals("partai")
										&& !candidate.get(key).equals(null))
									value = candidate.getJSONObject(key)
											.getString("nama");
								if (key.equals("foto_url")) {
									photoUrl = candidate.getString(key);
									// return;
								}
								key = key.replace("_", " ");
								key = WordUtils.capitalizeFully(key);

								if (!key.equals("Foto Url")) {
									CandidateDetail detail = new CandidateDetail(
											key, value);
									detailList.add(detail);
								}
							}
							adapter = new CandidateDetailAdapter(
									CandidateDetailActivity.this, detailList);
							detailListView.setAdapter(adapter);
							Picasso.with(CandidateDetailActivity.this)
									.load(photoUrl).into(candidatePhoto);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
}
