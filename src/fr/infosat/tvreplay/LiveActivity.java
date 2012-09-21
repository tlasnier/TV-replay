//////////////////////////////////////////////////////////////
//															//
//		Activité de choix de la chaine en direct 			// 
//															//
//////////////////////////////////////////////////////////////

package fr.infosat.tvreplay;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import fr.infosat.broadcast.ContainerData;
import fr.infosat.broadcast.LiveChannel;
import fr.infosat.broadcast.Media;

public class LiveActivity extends Activity implements OnItemClickListener
{
	private File channelXML;
	private String xmlpath;
	private ArrayList<LiveChannel> channelList= new ArrayList<LiveChannel>();
	private Context context;
	private CustomAdapterLiveChannel listViewAdapter;


	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		xmlpath = Environment.getExternalStorageDirectory()+"/InfosatTV/";
		channelXML= new File(xmlpath, getString(R.string.xmlfile));

		//parser fichier xml dans la sdcard, seulement la partie intéressante(TV ou Radio)
		Media media =(Media) getIntent().getSerializableExtra("media");
		channelList=ContainerData.getChannels(channelXML, media);
		
		//list view pour afficher les chaines
		context =this;
		ListView list = new ListView(context);
		list.setAdapter(null);        
		listViewAdapter =  new CustomAdapterLiveChannel(context, channelList);
		list.setAdapter(listViewAdapter);
		list.setOnItemClickListener(this);
		setContentView(list);
	}

	public void onItemClick(AdapterView<?> arg0, View view,int pos, long id) 
	{
		Uri streamURL = Uri.parse(channelList.get(pos).getUrl());
		Intent streamIntent = new Intent(Intent.ACTION_VIEW);
		streamIntent.setData(streamURL);
		
		//	on teste l'existence d'une application capable de lire le flux.
		//	Si l'ArrayList contenant les applications convenables est vide, on affiche une erreur.
		//	sinon, on lance la nouvelle activité.
		if(context.getPackageManager().queryIntentActivities(streamIntent, 0).isEmpty())
			Toast.makeText(context, R.string.no_app_found, Toast.LENGTH_LONG).show();
		else
		{
			Toast.makeText(context, R.string.player_launched, Toast.LENGTH_LONG).show();
			startActivity(streamIntent);
		}
	}
}