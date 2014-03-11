package com.hackathon.pemilu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenuAdapter extends ArrayAdapter<String> {

	int resource;
	Context context;
	LayoutInflater inflater;
	
	String[] title;
	int[] images;

	public MainMenuAdapter(Context context, int resource, String[] title, int[] images) {
		super(context, resource, title);
		this.resource 	= resource;
		this.title 	= title;
		this.images	= images;
		
		inflater = ( LayoutInflater ) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public static class ViewHolder{
        public TextView menutitle;
        public ImageView background;
    }
	
	@Override
	public int getCount() {
		return title.length;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{

		View vi = convertView;
        ViewHolder holder;
         
        if( convertView == null ){
             
            vi = inflater.inflate( resource, null );
            
            holder = new ViewHolder();
            holder.menutitle 	= (TextView) 	vi.findViewById(R.id.rowText);
            holder.background 	= (ImageView) 	vi.findViewById(R.id.rowImageMenu);
             
            vi.setTag( holder );
        }
        else {
            holder = (ViewHolder) vi.getTag();
        }

        holder.menutitle.setText( title[position] );
        holder.background.setImageResource( images[position] );
		
		return vi;
	}
	
} 
