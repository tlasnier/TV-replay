package fr.infosat.tvreplay;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import fr.infosat.rss.Emission;
import fr.infosat.rss.Emission.Chaine;
import fr.infosat.rss.LecteurParChaine;


public class ChannelListing extends Activity implements OnItemClickListener
{
	private ArrayList<Chaine> m_Channels = new ArrayList<Chaine>();
	private ArrayList<Emission> m_Shows = new ArrayList<Emission>();
	CustomAdapterChannel lvAdapter;
	protected ProgressDialog mProgressDialog;
	private Context context;
	final Handler uiThreadCallback=new Handler();
	private Runnable runInUIThread;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		context =this;
		ListView ls2 = new ListView(context);

		// clear previous results in the LV
		ls2.setAdapter(null);        
		// populate
		m_Channels.clear();
		Chaine[] ls_channel =Chaine.values();
		for(int i=0;i<ls_channel.length;i++)
			m_Channels.add(ls_channel[i]);

		lvAdapter =  new CustomAdapterChannel(context, m_Channels);
		ls2.setAdapter(lvAdapter);
		ls2.setOnItemClickListener(this);

		setContentView(ls2);

		runInUIThread=new Runnable() {
			public void run()
			{
				Intent intent=new Intent(context, ShowListing.class);
				intent.putExtra("listShow", m_Shows);
				startActivity(intent);
			}
		};
	}

	public void onItemClick(AdapterView<?> arg0, View view,int pos, long id) 
	{
		chargerFlux(Chaine.values()[pos]);
	}

	public void chargerFlux(final Chaine channel)
	{
		mProgressDialog= ProgressDialog.show(context, "Veuillez patienter", "Récupération des informations", true);
		
		new Thread(){
			public void run()
			{
				LecteurParChaine reader = new LecteurParChaine();
				m_Shows=reader.raffarichirEmissions(channel);
				
				uiThreadCallback.post(runInUIThread);
				
				mProgressDialog.dismiss();
			}
		}.start();

	}
}