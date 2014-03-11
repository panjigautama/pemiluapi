package com.hackathon.pemilu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SelectCandidateActivity extends SherlockActivity {
	HackathonApplication application;
	SessionManager session;
	ListView listView;
	int party_id;
	ArrayList<Candidate> candidateList;
	CandidateListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_candidate);
		application = (HackathonApplication) getApplicationContext();
		session = application.getSession();
		listView = (ListView) findViewById(R.id.dpr_listview);
		listView.setEmptyView(findViewById(R.id.progressBar));

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			party_id = bundle.getInt("party_id");
			session.setPartyId(party_id);
		}

		candidateList = new ArrayList<Candidate>();
		RequestParams params = new RequestParams();
		params.put("lembaga", "DPR");
		params.put("apiKey", Constants.PEMILUAPI_KEY);
		params.put("provinsi", String.valueOf(session.getProvinceId()));
		params.put("partai", String.valueOf(party_id));

		HackathonRESTClient.get("/candidate/api/caleg", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						Gson gson = new Gson();
						JSONArray candidateArray;
						try {
							candidateArray = response.getJSONObject("data")
									.getJSONObject("results")
									.getJSONArray("caleg");
							for (int i = 0; i < candidateArray.length(); i++) {
								Candidate candidate = gson.fromJson(
										candidateArray.getJSONObject(i)
												.toString(), Candidate.class);
								candidateList.add(candidate);
							}
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									adapter = new CandidateListAdapter(
											SelectCandidateActivity.this,
											candidateList);
									listView.setAdapter(adapter);
									listView.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {
											Intent i = new Intent(
													SelectCandidateActivity.this,
													CandidateDetailActivity.class);
											i.putExtra("candidate_id",
													candidateList.get(position)
															.getId());
											startActivity(i);
										}
									});
								}
							});

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
}
