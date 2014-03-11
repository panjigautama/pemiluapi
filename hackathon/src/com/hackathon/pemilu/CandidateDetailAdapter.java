package com.hackathon.pemilu;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CandidateDetailAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<CandidateDetail> detailList;
	private LayoutInflater inflater;

	public CandidateDetailAdapter(Context context,
			ArrayList<CandidateDetail> detailList) {
		this.context = context;
		this.detailList = detailList;
		this.inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return detailList.size();
	}

	@Override
	public Object getItem(int position) {
		return detailList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		View view = convertView;
		TextView keyText, valueText;
		CandidateDetail candidateDetail = (CandidateDetail) getItem(position);
		if (view == null) {
			view = inflater.inflate(R.layout.list_item_candidate_detail, null);
		}
		keyText = (TextView) view.findViewById(R.id.detail_key);
		valueText = (TextView) view.findViewById(R.id.detail_value);
		keyText.setText(candidateDetail.getKey());
		valueText.setText(candidateDetail.getValue());
		return view;
	}

}
