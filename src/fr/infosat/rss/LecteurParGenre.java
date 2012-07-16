package fr.infosat.rss;
import java.util.ArrayList;

import org.w3c.dom.Node;

import fr.infosat.rss.Emission.Genre;

public class LecteurParGenre extends RSSReader
{
	private ArrayList<Emission> listeEmissions=new ArrayList<Emission>();

	public ArrayList<Emission> raffarichirEmissions(Genre genre)
	{
		listeEmissions.clear();
		parse("http://feeds.feedburner.com/Pluzz-"+genre.getNomURL()+"?format=xml1");
				for(Emission e : listeEmissions)
			e.setGenre(genre);
		return listeEmissions;
	}
	
	public void traiterElement(Node element)
	{
		Emission em= new Emission();
		em.setTitre(readNode(element, "title"));
		em.setUrl(readNode(element, "link"));
		em.setDate(GMTDateToFrench(readNode(element, "pubDate")));
		em.setDescription(getDescription(element, "description"));
		em.setChaine(null);
		listeEmissions.add(em);
	}

	public ArrayList<Emission> getListeEmissions(){return listeEmissions;}
}
