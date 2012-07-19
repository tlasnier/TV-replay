package fr.infosat.broadcast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.content.Context;

public class ContainerData
{	
	static public Context context;

	public ContainerData() {}

	public static ArrayList<LiveChannel> getChannels(File file, Media media)
	{
		// On passe par une classe factory pour obtenir une instance de sax
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = null;
		try
		{
			parser = factory.newSAXParser();
		}

		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}

		catch (SAXException e)
		{
			e.printStackTrace();
		}

		/*
		 * Le handler sera gestionnaire du fichier XML c'est à dire que c'est lui qui sera chargé
		 * des opérations de parsing. On vera cette classe en détails ci après.
		 */
		ParserXMLHandler handler = new ParserXMLHandler(media);
		try
		{
			parser.parse(file, handler);
		}
		
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return handler.getData();
	}
}