package com.hackathon.pemilu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DPRFragment extends SherlockFragment {

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

		View view = inflater.inflate(R.layout.fragment_dpr, null);
		final ListView candidateListView = (ListView) view
				.findViewById(R.id.dpr_listview);
		candidateListView.setEmptyView(view.findViewById(R.id.progressBar));

		candidateList = new ArrayList<Candidate>();

		RequestParams params = new RequestParams();
		params.put("lembaga", "DPR");
		params.put("apiKey", Constants.PEMILUAPI_KEY);
		params.put("provinsi", String.valueOf(session.getProvinceId()));
		params.put("partai", String.valueOf(session.getPartyId()));
		System.out.println(session.getProvinceId());
		System.out.println(session.getPartyId());

		HackathonRESTClient.get("/candidate/api/caleg", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						Gson gson = new Gson();
						JSONArray candidateArray;
						try {
							System.out.println(response.toString());
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
