package fr.infosat.tvreplay;


import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.infosat.rss.Emission.Genre;


class CustomAdapterViewTheme extends LinearLayout
{        
	public CustomAdapterViewTheme(Context context, Genre theme) 
	{
		super(context);

		//container is a horizontal layer
		setOrientation(LinearLayout.HORIZONTAL);
		setPadding(0, 6, 0, 6);
		setGravity(Gravity.CENTER_VERTICAL);

		//Nom de la chaine
		TextView textName = new TextView(context);
		textName.setTextSize(16);
		textName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		textName.setText(theme.getLabel());
		addView(textName);
	}
}


public class CustomAdapterTheme extends BaseAdapter
{
	public static final String LOG_TAG = "BI::CA";
	private Context context;
	private List<Genre> themeList;

	public CustomAdapterTheme(Context context, List<Genre> themeList )
	{ 
		this.context = context;
		this.themeList = themeList;
	}

	public int getCount()
	{                        
		return themeList.size();
	}

	public Object getItem(int position)
	{     
		return themeList.get(position);
	}

	public long getItemId(int position)
	{  
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{ 
		Genre theme= themeList.get(position);
		View v = new CustomAdapterViewTheme(this.context, theme);

		v.setBackgroundColor(Color.BLACK);

		return v;
	}
}
