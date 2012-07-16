package fr.infosat.rss;
import java.util.ArrayList;
import java.util.Arrays;

import org.w3c.dom.Node;

import fr.infosat.rss.Emission.Chaine;

public class LecteurParChaine extends RSSReader
{
	private ArrayList<Emission> listeEmissions=new ArrayList<Emission>();
	private ArrayList<Chaine> listeChainePluzz=new ArrayList<Chaine>(Arrays.asList(
			Chaine.La_1ere,
			Chaine.France2,
			Chaine.France3,
			Chaine.France4,
			Chaine.France5,
			Chaine.Franceo));
	
	public ArrayList<Emission> raffarichirEmissions(Chaine chaine)
	{
		listeEmissions.clear();
		if(listeChainePluzz.contains(chaine))
			parse("http://feeds.feedburner.com/Pluzz-"+chaine.getNomURL()+"?format=xml");
		else
			System.out.println("Il reste à trouver la source d'émissions pour cette chaine... Désolé");
		
		for(Emission e : listeEmissions)
			e.setChaine(chaine);
		return listeEmissions;
	}
	
	public void traiterElement(Node element)
	{
		Emission em= new Emission();
		em.setTitre(readNode(element, "title"));
		em.setUrl(readNode(element, "link"));
		em.setDate(GMTDateToFrench(readNode(element, "pubDate")));
		em.setDescription(getDescription(element, "description"));
		em.setGenre(null);
		listeEmissions.add(em);
	}

	public ArrayList<Emission> getListeEmissions(){return listeEmissions;}

}
