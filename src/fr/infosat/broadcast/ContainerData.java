//////////////////////////////////////////////////////////////
//															//
//		Parsing d'un fichier XML via une m�thode static		//
//															//
//////////////////////////////////////////////////////////////

package fr.infosat.broadcast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class ContainerData
{	
	//M�thode qui renvoie une liste de Cha�nes, selon le m�dia s�lectionn� (TV ou Radio)
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

		//	Le handler est une instance qui se charge du parsing du fichier.
		//	On d�clare une instance d'une sous-classe pour lui donner les propri�t�s que l'on souhaite.
		//	En l'occurence, parser le fichier XML que l'on �crit nous-m�me.
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