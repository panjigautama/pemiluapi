package com.hackathon.pemilu;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CandidateListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Candidate> candidateList;
	private LayoutInflater inflater;

	public CandidateListAdapter(Context context,
			ArrayList<Candidate> candidateList) {
		this.context = context;
		this.candidateList = candidateList;
		this.inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return candidateList.size();
	}

	@Override
	public Object getItem(int position) {
		return candidateList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.list_item_candidate, null);
		}
		Candidate candidate = (Candidate) getItem(position);
		ImageView candidatePhoto = (ImageView) view
				.findViewById(R.id.candidate_photo);
		TextView candidateName = (TextView) view
				.findViewById(R.id.candidate_name);

		Picasso.with(context).load(candidate.getFoto_url())
				.into(candidatePhoto);
		candidateName.setText(candidate.getNama());

		return view;
	}
}
