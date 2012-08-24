//////////////////////////////////////////////////////////
//														//
//		Activité du choix de l'émission en Replay		// 
//														//
//////////////////////////////////////////////////////////

package fr.infosat.tvreplay;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import fr.infosat.rss.Emission;

public class ShowListing extends Activity implements OnItemClickListener
{
	private ArrayList<Emission> m_Shows = new ArrayList<Emission>();
	private CustomAdapterShows listViewAdapter;

	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		final Context context = this;
		Intent intent=getIntent();
		m_Shows= (ArrayList<Emission>) intent.getSerializableExtra("listShow");
		ListView list = new ListView(context);

		list.setAdapter(null);        
		if(!m_Shows.isEmpty())
		{
			listViewAdapter =  new CustomAdapterShows(context, m_Shows);
			list.setAdapter(listViewAdapter);
			list.setOnItemClickListener(this);
			setContentView(list);
		}
		
		else
		{
			Toast.makeText(context, R.string.erreur_emission, Toast.LENGTH_LONG).show();
		}
	}

	public void onItemClick(AdapterView<?> arg0, View view,int pos, long id) 
	{
		Emission show=m_Shows.get(pos);
		Uri webPage = Uri.parse(show.getUrl());
		Intent webIntent = new Intent(Intent.ACTION_VIEW);
		webIntent.setData(webPage);		
		startActivity(webIntent);
	}
}