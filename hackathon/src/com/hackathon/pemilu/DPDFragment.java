package com.hackathon.pemilu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DPDFragment extends SherlockFragment {

	private CandidateListAdapter adapter;
	private ArrayList<Candidate> candidateList;
	private HackathonApplication application;
	private SessionManager session;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		application = (HackathonApplication) getSherlockActivity()
				.getApplicationContext();
		session = application.getSession();

		View view = inflater.inflate(R.layout.fragment_dpd, null);
		final ListView candidateListView = (ListView) view
				.findViewById(R.id.dpd_listview);
		candidateListView.setEmptyView(view.findViewById(R.id.progressBar));

		candidateList = new ArrayList<Candidate>();

		RequestParams params = new RequestParams();
		params.put("lembaga", "DPD");
		params.put("apiKey", Constants.PEMILUAPI_KEY);
		params.put("provinsi", String.valueOf(session.getProvinceId()));

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
							getSherlockActivity().runOnUiThread(new Runnable() {

								@Override
								public void run() {
									adapter = new CandidateListAdapter(
											getSherlockActivity(),
											candidateList);
									candidateListView.setAdapter(adapter);
									candidateListView
											.setOnItemClickListener(new OnItemClickListener() {

												@Override
												public void onItemClick(
														AdapterView<?> parent,
														View view,
														int position, long id) {
													Intent i = new Intent(
															getSherlockActivity(),
															CandidateDetailActivity.class);
													i.putExtra(
															"candidate_id",
															candidateList.get(
																	position)
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

		return view;
	}
}
