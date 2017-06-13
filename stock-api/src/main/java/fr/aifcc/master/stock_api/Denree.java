package fr.aifcc.master.stock_api;

public class Denree extends Item //Classe qui permer d'acceder a l'objet Denree
{
	//atribut de classe
	private String nom ;
	private long quantite ;
	private String categorie ;

	//constructeur vide de la classe
	public Denree()
	{
		this.nom = "";
		this.quantite = 0;
		this.categorie = "";
	}

	public String getNom(){
		return this.nom ;
	}
	public void setNom(String nom){
		this.nom = nom ;
	}

	public long getQuantite(){
		return this.quantite ;
	}
	public void setQuantite(long quantite){
		this.quantite = quantite ;
	}

	public String getCategorie(){
		return this.categorie ;
	}
	public void setCategorie(String categorie){
		this.categorie = categorie ;
	}
}