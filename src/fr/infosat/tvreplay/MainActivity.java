package fr.infosat.tvreplay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity
{
	private String xmlpath;
	private File channelXML;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		xmlpath = Environment.getExternalStorageDirectory()+"/InfosatTV/";
		channelXML = new File(xmlpath, getString(R.string.xmlfile));
	}
	
	public void replayActivity(View view)
	{
		Intent intent=new Intent(this,ReplayActivity.class);
		startActivity(intent);
	}
	
	public void liveActivity(View view)
	{
		if (!channelXML.exists() || !channelXML.isFile())
			refreshChannels();
		
		Intent intent = new Intent(this, LiveActivity.class);
		startActivity(intent);
//		Uri streamURL = Uri.parse("udp://230.0.0.3:1234");
//		Intent streamIntent = new Intent(Intent.ACTION_VIEW);
//		streamIntent.setData(streamURL);	
//		startActivity(streamIntent);
		
	}	
	
	public void radioActivity(View view)
	{
//		Intent intent = new Intent(this, radioActivity.class);
//		startActivity(intent);
	}
	
	private void refreshChannels()
	{

		createDirIfNotExists(xmlpath);
		URL url=null;
		
		try
		{
			url = new URL(getString(R.string.xmlurl));
		} 
		
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		downloadFromInternet(channelXML,url);
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
	
	private void downloadFromInternet(File file, URL url )
	{
		try
		{
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);

			urlConnection.connect();

			FileOutputStream fileOutput = new FileOutputStream(file);

			InputStream inputStream = urlConnection.getInputStream();

			byte[] buffer = new byte[1024];
			int bufferLength = 0;

			while ( (bufferLength = inputStream.read(buffer)) > 0 )
				fileOutput.write(buffer, 0, bufferLength);

			fileOutput.close();

		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
