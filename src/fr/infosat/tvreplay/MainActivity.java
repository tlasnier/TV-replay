package fr.infosat.tvreplay;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import fr.infosat.broadcast.Media;

public class MainActivity extends Activity
{
	private String xmlpath;
	private File channelXML;
	private Context m_context;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		xmlpath = Environment.getExternalStorageDirectory()+"/InfosatTV/";
		channelXML = new File(xmlpath, getString(R.string.xmlfile));
		m_context=this;		
	}

	public void replayActivity(View view)
	{
		Intent intent=new Intent(this,ReplayActivity.class);
		startActivity(intent);
	}

	public void liveActivity(View view)
	{
		if (!channelXML.exists() )
			refreshChannels();
		if(channelXML.exists())
		{
			Intent intent = new Intent(this, LiveActivity.class);
			intent.putExtra("media", Media.TV);
			startActivity(intent);
		}
	}	

	public void radioActivity(View view)
	{
		if (!channelXML.exists() )
			refreshChannels();
		if(channelXML.exists())
		{
			Intent intent = new Intent(this, LiveActivity.class);
			intent.putExtra("media", Media.RADIO);
			startActivity(intent);
		}
	}

	private void refreshChannels()
	{

		createDirIfNotExists(xmlpath);
		URL url=null;
		boolean b = channelXML.exists(); if(b) b=true;
		try
		{
			url = new URL(getString(R.string.xmlurl));
		} 

		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

		Toast t = Toast.makeText(m_context,"",Toast.LENGTH_LONG);
		t.setDuration(Toast.LENGTH_LONG);
		if (InternetConnection.hasActiveInternetConnection(m_context))
		{
			InternetConnection.downloadFromInternet(channelXML,url);
			t.setText(R.string.refreshdone);
		}
		else
		{
			t.setText(R.string.no_connection);
		}
		t.show();
	}

	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.option, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.refresh:
			refreshChannels();
			return true;
		case R.id.quit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean createDirIfNotExists(String path)
	{
		boolean ret = true;

		File file = new File(path);
		if (!file.exists())
		{
			if (!file.mkdirs())
			{
				ret = false;
			}
		}
		return ret;
	}
}
