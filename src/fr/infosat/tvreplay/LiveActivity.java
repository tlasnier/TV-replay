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
	private CustomAdapterLiveChannel lvAdapter;


	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		xmlpath = Environment.getExternalStorageDirectory()+"/InfosatTV/";
		channelXML= new File(xmlpath, getString(R.string.xmlfile));

		//parser fichier xml dans la sdcard
		Media media =(Media) getIntent().getSerializableExtra("media");
		channelList=ContainerData.getChannels(channelXML, media);
		
		//list view pour présenter les chaines
		context =this;
		ListView ls2 = new ListView(context);
		ls2.setAdapter(null);        
		lvAdapter =  new CustomAdapterLiveChannel(context, channelList);
		ls2.setAdapter(lvAdapter);
		ls2.setOnItemClickListener(this);
		setContentView(ls2);
	}

	public void onItemClick(AdapterView<?> arg0, View view,int pos, long id) 
	{
		Uri streamURL = Uri.parse(channelList.get(pos).getUrl());
		Intent streamIntent = new Intent(Intent.ACTION_VIEW);
		streamIntent.setData(streamURL);	
		if(context.getPackageManager().queryIntentActivities(streamIntent, 0).isEmpty())
		{
			Toast t = Toast.makeText(context, R.string.no_app_found, Toast.LENGTH_LONG);
			t.show();
		}
		else
			startActivity(streamIntent);
	}
}