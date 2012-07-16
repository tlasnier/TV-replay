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
import fr.infosat.rss.Emission.Genre;
import fr.infosat.rss.LecteurParGenre;


public class ThemeListing extends Activity implements OnItemClickListener
{
	private ArrayList<Genre> m_Themes = new ArrayList<Genre>();
	private ArrayList<Emission> m_Shows = new ArrayList<Emission>();
	CustomAdapterTheme lvAdapter;
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
		m_Themes.clear();
		Genre[] ls_theme =Genre.values();
		for(int i=0;i<ls_theme.length;i++)
			m_Themes.add(ls_theme[i]);
		
		lvAdapter =  new CustomAdapterTheme(context, m_Themes);
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
		chargerFlux(Genre.values()[pos]);
	}

	public void chargerFlux(final Genre theme)
	{
		mProgressDialog= ProgressDialog.show(context, "Veuillez patienter", "Récupération des informations", true);
		
		new Thread(){
			public void run()
			{
				LecteurParGenre reader = new LecteurParGenre();
				m_Shows=reader.raffarichirEmissions(theme);
				
				uiThreadCallback.post(runInUIThread);
				
				mProgressDialog.dismiss();
			}
		}.start();

	}
}