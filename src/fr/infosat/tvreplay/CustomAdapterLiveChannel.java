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
import fr.infosat.broadcast.LiveChannel;


class CustomAdapterViewLiveChannel extends LinearLayout
{        
	public CustomAdapterViewLiveChannel(Context context, LiveChannel channel) 
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
		String ic_name= cleanName(channel.getName());
		int logo_id = context.getResources().getIdentifier(ic_name, "drawable", context.getPackageName());
		
		if(logo_id==0)
			logo_id=context.getResources().getIdentifier("ic_launcher", "drawable", context.getPackageName());
		logoChannel.setImageDrawable(context.getResources().getDrawable(logo_id));
		//image:add
		addView(logoChannel, params);			

		TextView textName = new TextView(context);
		textName.setTextSize(16);
		textName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		textName.setText(channel.getName());
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


public class CustomAdapterLiveChannel extends BaseAdapter
{
	public static final String LOG_TAG = "BI::CA";
	private Context context;
	private List<LiveChannel> channelList;

	public CustomAdapterLiveChannel(Context context, List<LiveChannel> channelList )
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
		LiveChannel channel = channelList.get(position);
		View v = new CustomAdapterViewLiveChannel(this.context, channel);

		v.setBackgroundColor(Color.BLACK);

		return v;
	}
}
