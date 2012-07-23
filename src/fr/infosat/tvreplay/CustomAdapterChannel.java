package fr.infosat.tvreplay;


import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.infosat.rss.Emission.Chaine;


class CustomAdapterViewChannel extends LinearLayout
{        
	public CustomAdapterViewChannel(Context context, Chaine channel) 
	{
		super(context);

		//container is a horizontal layer
		setOrientation(LinearLayout.HORIZONTAL);
		setPadding(0, 6, 0, 6);
		setGravity(Gravity.CENTER_VERTICAL);
		
		//image:params
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80, LayoutParams.WRAP_CONTENT);
		params.setMargins(6, 0, 6, 0);
		//image:itself
		ImageView logoChannel = new ImageView(context);
		logoChannel.setMinimumHeight(60);
		// load image
		int logo_id = context.getResources().getIdentifier(cleanName(channel.name()), "drawable", context.getPackageName());
		
		if(logo_id==0)
			logo_id=context.getResources().getIdentifier("ic_launcher", "drawable", context.getPackageName());
		logoChannel.setImageDrawable(context.getResources().getDrawable(logo_id));
		//image:add
		addView(logoChannel, params);			

		TextView textName = new TextView(context);
		textName.setTextSize(16);
		textName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		textName.setText(channel.getLabel());
		addView(textName);
	}
	
	private String cleanName(String name)
	{
		String res = name.toLowerCase();
		res = Normalizer.normalize(res, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		String normalized = Normalizer.normalize(res, Form.NFD);
		res= normalized.replaceAll("[^A-Za-z0-9]", "");
 
		return res;
	}
}


public class CustomAdapterChannel extends BaseAdapter
{
	private Context context;
	private List<Chaine> channelList;

	public CustomAdapterChannel(Context context, List<Chaine> channelList )
	{ 
		this.context = context;
		this.channelList = channelList;
	}

	public int getCount()
	{                        
		return channelList.size();
	}

	public Object getItem(int position)
	{     
		return channelList.get(position);
	}

	public long getItemId(int position)
	{  
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{ 
		Chaine channel = channelList.get(position);
		View v = new CustomAdapterViewChannel(this.context, channel);

		v.setBackgroundColor(Color.BLACK);

		return v;
	}
}
