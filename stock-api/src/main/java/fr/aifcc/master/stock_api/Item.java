package fr.aifcc.master.stock_api;

public abstract class Item //Classe d'Objet generale
{
	//atribut de classe
	private long id ;

	public Item()
	{
		this.id = id ;
	}	

	public long getId(){
		return this.id ;
	}

	public void setId(long id){
		this.id = id ;
	}
}


