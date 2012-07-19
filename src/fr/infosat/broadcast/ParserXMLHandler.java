package fr.infosat.broadcast;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserXMLHandler extends DefaultHandler
{
	// nom des tags XML
	private final String ITEM = "item";
	private final String TITLE = "title";
	private final String LINK = "link";
	private Media media;

	private ArrayList<LiveChannel> entries;
	private boolean inItem,inMedia=false;
	private LiveChannel currentChannel;

	// Buffer permettant de contenir les données d'un tag XML
	private StringBuffer buffer;

	public ParserXMLHandler(Media m)
	{
		super();
		media=m;
	}

	public void processingInstruction(String target, String data) throws SAXException
	{
		super.processingInstruction(target, data);
	}

	public void startDocument() throws SAXException
	{
		super.startDocument();
		entries = new ArrayList<LiveChannel>();
	}

	public void startElement(String uri, String localName, String name,	Attributes attributes) throws SAXException
	{
		buffer = new StringBuffer();

		//si on entre dans un tag TV, ou Radio :
		if (localName.equalsIgnoreCase(media.name()))
		{
			inMedia=true;
		}

		//Nous avons rencontré un tag ITEM, il faut donc instancier une nouvelle chaine
		if (localName.equalsIgnoreCase(ITEM))
		{
			currentChannel = new LiveChannel();
			inItem = true;
		}
	}

	public void endElement(String uri, String localName, String name) throws SAXException
	{		
		if (inMedia)
		{
			if (localName.equalsIgnoreCase(TITLE))
			{
				if(inItem)
				{
					currentChannel.setName(buffer.toString());
					buffer = null;
				}
			}

			if (localName.equalsIgnoreCase(LINK))
			{
				if(inItem)
				{
					currentChannel.setUrl(buffer.toString());
					buffer = null;
				}
			}

			if (localName.equalsIgnoreCase(ITEM))
			{
				entries.add(currentChannel);
				inItem = false;
			}

			if (localName.equalsIgnoreCase(media.name()))
			{
				inMedia=false;
			}
		}
	}

	public void characters(char[] ch,int start, int length)	throws SAXException
	{
		String lecture = new String(ch,start,length);
		if(buffer != null) buffer.append(lecture);
	}

	public ArrayList<LiveChannel> getData()
	{
		return entries;
	}
}