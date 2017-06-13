package fr.aifcc.master.directory_api;

public class Denree extends Item
{
	//atribut de classe
	private String nom ;
	private String categorie ;

	//constructeur vide de la classe
	public Person()
	{
		this.firstName = "";
		this.lastName = "";
	}

	public String getNom(){
		return this.nom ;
	}
	public void setNom(String nom){
		this.nom = nom ;
	}

	public String getCategorie(){
		return this.categorie ;
	}
	public void setCategorie(String categorie){
		this.categorie = categorie ;
	}
}