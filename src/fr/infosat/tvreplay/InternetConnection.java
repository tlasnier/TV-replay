//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//																														//
//		Classe d'utilisation d'une connection Internet (Téléchargement d'un fichier, test d'une connexion...) 			// 
//																														//
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package fr.infosat.tvreplay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnection
{
	public static boolean hasActiveInternetConnection(Context context)
	{
		if (isNetworkAvailable(context))
		{
			try
			{
				HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
				urlc.setRequestProperty("User-Agent", "Test");
				urlc.setRequestProperty("Connection", "close");
				urlc.setConnectTimeout(1500); 
				urlc.connect();
				return (urlc.getResponseCode() == 200);
			}
			catch (IOException e){}
		}
		return false;
	}

	private static boolean isNetworkAvailable(Context context)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	public static void downloadFromInternet(File file, URL url )
	{
		BufferedReader in;
		String readLine;
		try
		{
			in = new BufferedReader(new InputStreamReader(url.openStream(), "ISO-8859-1"));
			BufferedWriter out = new BufferedWriter(new FileWriter(file));

			while ((readLine = in.readLine()) != null)
				out.write(readLine+"\n");

			out.close();
		}

		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
