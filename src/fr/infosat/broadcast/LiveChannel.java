package fr.infosat.broadcast;

public class LiveChannel
{
	private String name;
	private String url;

	public LiveChannel()
	{
		setName("");
		setUrl("");
	}
	
	public LiveChannel(String n, String u)
	{
		setName(n);
		setUrl(u);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}	
}
