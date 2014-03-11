package com.hackathon.pemilu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ReportImageAdapter extends ArrayAdapter<ReportImage> {

	int resource;
	List<ReportImage> status;
	Context context;
	LayoutInflater inflater;

	public ReportImageAdapter(Context context, int resource, List<ReportImage> status) {
		super(context, resource, status);
		this.resource 	= resource;
		this.status 	= status;
		this.context	= context;
		
		inflater = ( LayoutInflater ) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public static class ViewHolder{
        public TextView username;
        public TextView date;
        public TextView source;
        public TextView caption;
        public SquareImageView image;
    }
	
	@Override
	public int getCount() {
		return status.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{

		View vi = convertView;
        ViewHolder holder;
         
        if( convertView == null ){
             
            vi = inflater.inflate( resource, null );
            
            holder = new ViewHolder();
            holder.date 	= 	(TextView) 	vi.findViewById(R.id.report_date);
            holder.username = 	(TextView) 	vi.findViewById(R.id.report_username);
            holder.caption	=	(TextView)  vi.findViewById(R.id.report_caption);
            holder.image	=	(SquareImageView) vi.findViewById(R.id.report_image);
            holder.source	=	(TextView) 	vi.findViewById(R.id.report_source);
             
            vi.setTag( holder );
        }
        else {
            holder = (ViewHolder) vi.getTag();
        }

        ReportImage item = status.get(position);
				
		// image
//		MediaEntity[] media = item.getMediaEntities();
//		if( media.length > 0 ) {
//			String url = media[0].getMediaURL();
//			Picasso.with( context ).load( url ).into( holder.image );
//		}
		Picasso.with( context ).load( item.getImageLink() ).into( holder.image );
		
		// image
//		holder.username.setText( item.getUser().getName() );
		holder.username.setText( item.getUsername() );

		// caption
//		holder.caption.setText( item.getText() );
		holder.caption.setText( item.getCaption() );
		
		// date
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = item.getDate();
		String dateformat = sdf.format( item.getDate() );
		
		String source = "";
		switch( item.getType() )
		{
			case ReportImage.TWITTER:
				source = "twitter";
				break;
			case ReportImage.INSTAGRAM:
				source = "instagram";
				break;
			case ReportImage.FACEBOOK:
				source = "facebook";
				break;
		}
		
		holder.date.setText( dateformat );
		holder.source.setText( source );
		
		return vi;
	}
	
} 
