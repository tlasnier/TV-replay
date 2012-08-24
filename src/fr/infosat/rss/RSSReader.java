//////////////////////////////////////////////////
//												//
//		Classe de lecture d'un flux RSS			// 
//												//
//////////////////////////////////////////////////

package fr.infosat.rss;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class RSSReader
{
	public void parse(String feedurl)
	{
		try
		{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			URL url = new URL(feedurl);
			Document doc = builder.parse(url.openStream());
			NodeList nodes = null;
			Node element = null;

			nodes = doc.getElementsByTagName("item");
			for (int i = 0; i < nodes.getLength(); i++) {
				element = nodes.item(i);
				traiterElement(element);
			} 
		}
		
		catch (SAXException ex)
		{
			Logger.getLogger(RSSReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		catch (IOException ex)
		{
			Logger.getLogger(RSSReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		catch (ParserConfigurationException ex)
		{
			Logger.getLogger(RSSReader.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public String readNode(Node _node, String _path)
	{
		String[] paths = _path.split("\\|");
		Node node = null;

		if (paths != null && paths.length > 0)
		{
			node = _node;

			for (int i = 0; i < paths.length; i++)
				node = getChildByName(node, paths[i].trim());
		}

		if (node != null)
			return node.getTextContent();
		else
			return "";
	}

	public Node getChildByName(Node _node, String _name)
	{
		if (_node == null)
			return null;
		
		NodeList listChild = _node.getChildNodes();

		if (listChild != null) 
			for (int i = 0; i < listChild.getLength(); i++)
			{
				Node child = listChild.item(i);
				if (child != null)
					if ((child.getNodeName() != null && (_name.equals(child.getNodeName()))) || (child.getLocalName() != null && (_name.equals(child.getLocalName()))))
						return child;
			}
		
		return null;
	}

	//M�thode de conversion de format des dates 
	public String GMTDateToFrench(String gmtDate)
	{
		try
		{
			SimpleDateFormat dfGMT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
			dfGMT.parse(gmtDate);
			SimpleDateFormat dfFrench = new SimpleDateFormat("EEEE, d MMMM HH:mm", Locale.FRANCE);
			return dfFrench.format(dfGMT.getCalendar().getTime());
		}
		
		catch (ParseException ex)
		{
			Logger.getLogger(RSSReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return "";
	}
	
	public String getDescription(Node _node, String _path)
	{
		String totalite =readNode(_node,_path);
		String[] partition = totalite.split("<");
		return partition[0];
	}
	
	//M�thode g�n�rique � impl�menter selon le traitement � effectuer
	public abstract void traiterElement(Node element);
}
