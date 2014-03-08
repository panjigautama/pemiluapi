package com.hackathon.pemilu;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Party> partiesList;

	public ImageAdapter(Context context, ArrayList<Party> partiesList) {
		this.context = context;
		this.partiesList = partiesList;
	}

	@Override
	public int getCount() {
		return partiesList.size();
	}

	@Override
	public Object getItem(int position) {
		return partiesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		ImageView partyLogo = new ImageView(context);
		final Party party = (Party) getItem(position);
		Picasso.with(context).load(party.getImageUrl()).into(partyLogo);
		partyLogo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent(context, SelectCandidateActivity.class);
				i.putExtra("party_id", party.getId());
				context.startActivity(i);
			}
		});
		return partyLogo;
	}

}
