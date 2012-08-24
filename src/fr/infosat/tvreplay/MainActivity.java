//////////////////////////////////////////////
//											//
//		Activité principale : Menu 			// 
//											//
//////////////////////////////////////////////

package fr.infosat.tvreplay;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
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
		//vérifcation de la présence de la carte sd sur laquelle il y a des fichiers indispensables.
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			setContentView(R.layout.menu);
			xmlpath = Environment.getExternalStorageDirectory()+"/InfosatTV/";
			channelXML = new File(xmlpath, getString(R.string.xmlfile));
			m_context=this;	
		}

		//sinon affichage d'un message et sortie de l'application
		else
			(new AlertDialog.Builder(this)).setPositiveButton("OK", new PositiveListener()).setTitle(R.string.error).setMessage(R.string.no_sd_card).show();
	}

	//lancement de l'activité du Replay
	public void replayActivity(View view)
	{
		Intent intent=new Intent(this,ReplayActivity.class);
		startActivity(intent);
	}

	//lancement de l'activité du Direct
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

	//lancement de l'activité de la Radio
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
		//création du répertoire s'il n'existe pas
		createDirIfNotExists(xmlpath);
		URL url=null;
		if(channelXML.exists())
		{
			try
			{
				url = new URL(getString(R.string.xmlurl));
			} 

			catch (MalformedURLException e)
			{
				e.printStackTrace();
			}
		}

		//téléchargement du fichier dans un thread séparé
		new DownloadFileTask().execute(url);
	}

	//Gestion du bouton Menu
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
		case R.id.help:	
			Intent intent = new Intent(this, HelpActivity.class);
			startActivity(intent);
		case R.id.quit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean createDirIfNotExists(String path)
	{
		boolean result = true;

		File file = new File(path);
		if (!file.exists())
		{
			if (!file.mkdirs())
			{
				result = false;
			}
		}
		return result;
	}

	//Classe interne héritée de AsyncTask pour effectuer le téléchargement dans un thread séparé
	private class DownloadFileTask extends AsyncTask<URL, Void, Boolean>
	{
		protected Boolean doInBackground(URL... urls)
		{
			if (InternetConnection.hasActiveInternetConnection(m_context))
			{
				InternetConnection.downloadFromInternet(channelXML,urls[0]);
				return true;
			}
			else
				return false;
		}

		protected void onPostExecute(Boolean done)
		{
			Toast t = Toast.makeText(m_context,"",Toast.LENGTH_LONG);
			t.setDuration(Toast.LENGTH_LONG);

			if (done)
				t.setText(R.string.refreshdone);
			else
				t.setText(R.string.no_connection);
			t.show();
		}
	}

	private class PositiveListener implements OnClickListener
	{
		public void onClick(DialogInterface dialog, int which)
		{
			finish();
		}
	}
}
