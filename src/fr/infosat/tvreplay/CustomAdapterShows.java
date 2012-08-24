//////////////////////////////////////////////////////////////////////////////////////////////////
//																								//
//		Classe de description de l'affichage d'une liste de d'émissions en rediffusion 			// 
//																								//
//////////////////////////////////////////////////////////////////////////////////////////////////

package fr.infosat.tvreplay;


import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.infosat.rss.Emission;


class CustomAdapterView extends LinearLayout
{        
	public CustomAdapterView(Context context, Emission show) 
	{
		super( context );

		setOrientation(LinearLayout.HORIZONTAL);
		setPadding(6, 6, 6, 6);

		LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout PanelV = new LinearLayout(context);
		PanelV.setOrientation(LinearLayout.VERTICAL);
		PanelV.setGravity(Gravity.BOTTOM);

		TextView textName = new TextView(context);
		textName.setTextSize(16);
		textName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		textName.setText( show.getTitre()+" ("+show.getDate()+") ");
		PanelV.addView(textName);       

		TextView synopsis = new TextView(context);
		synopsis.setEllipsize(TruncateAt.END);
		synopsis.setTextSize(16);
		synopsis.setHorizontallyScrolling(true);
		synopsis.setText(show.getDescription());
		PanelV.addView(synopsis);
			
		addView(PanelV, Params);
	}
}


public class CustomAdapterShows extends BaseAdapter
{
	private Context context;
	private List<Emission> showList;

	public CustomAdapterShows(Context context, List<Emission> showList )
	{ 
		this.context = context;
		this.showList = showList;
	}

	public int getCount()
	{                        
		return showList.size();
	}

	public Object getItem(int position)
	{     
		return showList.get(position);
	}

	public long getItemId(int position)
	{  
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{ 
		Emission show = showList.get(position);
		View v = new CustomAdapterView(this.context, show );

		v.setBackgroundColor(Color.BLACK);

		return v;
	}
}
