//////////////////////////////////////////////////////
//													//
//		Classe de description d'une Emission		// 
//													//
//////////////////////////////////////////////////////

package fr.infosat.rss;

import java.io.Serializable;

public class Emission implements Serializable
{
	private String titre;
	private String url;
	private String date;
	private Chaine chaine;
	private Genre genre;
	private String description;
	
	
	public enum Genre
	{
		Info("Info","info"),
		Documentaire("Documentaire","documentaire"),
		SerieFiction("Série & Fiction","serie&fiction"),
		Magazine("Magazine","magazine"),
		Culture("Culture","culture"),
		Jeunesse("Jeunesse","jeunesse"),
		Divertissement("Divertissement","divertissement"),
		Sport("Sport","sport"),
		Jeu("Jeu","jeu"),
		Autre("Autre","autre");
		
		private String label,nomURL;
		
		Genre(String l,String n){label=l;nomURL=n;}
		
		public String getLabel(){return label;}
		public String getNomURL(){return nomURL;}
		public String toString(){return label;}
	};
	
	public enum Chaine
	{
		TF1("TF1", "tf1"),
		La_1ere("La 1ère","la_1ere"),
		France2("France 2","france2"),
		France3("France 3","france3"),
		France4("France 4","france4"),
		France5("France 5","france5"),
		M6("M6","m6"),
		Arte("Arte","arte"),
		Direct8("Direct 8","direct8"),
		W9("W9","w9"),
		TMC("TMC","tmc"),
		NT1("NT1","nt1"),
		NRJ12("NRJ12","nrj12"),
		LCP("LCP","lcp"),
		BFMTV("BFM TV","bfmtv"),
		ITELE("I>Télé","itele"),
		DirectStar("DirectStar","directstar"),
		Gulli("Gulli","gulli"),
		Franceo("France Ô","franceo");
		
		private String label, nomURL;
		
		Chaine(String l, String n){label=l;nomURL=n;}
		
		public String getLabel(){return label;}
		public String getNomURL(){return nomURL;}
		public String toString(){return label;}
	};
	
	
	public Emission(){}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Chaine getChaine() {
		return chaine;
	}

	public void setChaine(Chaine chaine) {
		this.chaine = chaine;
	}
	
	public Genre getGenre()
	{
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	public String getDescription() {
	return description;
}

	public void setDescription(String description) {
	this.description = description;
}

	public String toString()
	{
		String res="";
		if(titre!=null && titre!="")
			res+="Titre : "+titre+"\n";
		if(chaine!=null)
			res+="Chaine : "+chaine+"\n";
		if(url!=null && url!="")
			res+="URL : "+url+"\n";
		if(description!=null &&description!="")
			res+="Description : "+description+"\n";
		if(genre!=null)
			res+="Genre : "+genre+"\n";		
		if(date!=null&& date!="")
			res+="Date : "+date+"\n";		
		
		return res;
	}
}
