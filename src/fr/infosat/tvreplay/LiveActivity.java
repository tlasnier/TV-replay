package fr.infosat.tvreplay;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class LiveActivity extends Activity implements OnItemClickListener
{
	private File channelXML;
	private String xmlpath;
	private Bundle channelMap;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		xmlpath = Environment.getExternalStorageDirectory()+"/InfosatTV/";
		channelXML= new File(xmlpath, getString(R.string.xmlfile));
		//parser fichier xml dans la sdcard

		
		//list view pour présenter les chaines
		



		//implicit intent pour lancer mx player	

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		// TODO Auto-generated method stub

	}
}