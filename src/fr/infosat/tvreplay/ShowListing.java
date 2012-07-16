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
	CustomAdapterShows lvAdapter;

	/** Called when the activity is first created. */
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		final Context context = this;
		Intent intent=getIntent();
		m_Shows= (ArrayList<Emission>) intent.getSerializableExtra("listShow");
		ListView ls2 = new ListView(context);

		ls2.setAdapter(null);        
		if(!m_Shows.isEmpty())
		{
			lvAdapter =  new CustomAdapterShows(context, m_Shows);
			ls2.setAdapter(lvAdapter);
			ls2.setOnItemClickListener(this);
			setContentView(ls2);
		}
		
		else
		{
			Toast t= Toast.makeText(context, R.string.erreur_emission, Toast.LENGTH_LONG);
			t.show();
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