package fr.infosat.tvreplay;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void listThemes(View view)
	{
		Intent intent=new Intent(this,ThemeListing.class);
		startActivity(intent);
	}
	
	public void listChannels(View view)
	{
		Intent intent=new Intent(this,ChannelListing.class);
		startActivity(intent);
	}
	
	public void listStream(View view)
	{
		Uri streamURL = Uri.parse("udp://230.0.0.3:1234");
		Intent streamIntent = new Intent(Intent.ACTION_VIEW);
		streamIntent.setData(streamURL);	
		startActivity(streamIntent);
	}

}
