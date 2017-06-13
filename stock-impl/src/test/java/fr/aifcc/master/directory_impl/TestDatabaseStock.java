package fr.aifcc.master.stock_impl;

import java.util.Collection;
import fr.aifcc.master.stock_api.Denree;
import fr.aifcc.master.stock_api.Item;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.LinkedList;
import java.util.Iterator;

public class TestDatabaseStock
{
	protected DatabaseStock stock;
	protected final String driver = "org.apache.derby.jdbc.ClientDriver";
	protected final String url = "jdbc:derby://localhost:1527/TestStock";

    /**
     * @return La denree dont on veut afficher les détails.
     * @throws StockException
     * Un problème c'est produit lors de l'accès aux données.
     * */
	//Avant chaque test on cree une varibale de test
	@Before
	public void setUp() throws Exception{
		this.stock = new DatabaseStock(driver, url);
	}

    /**
     * @return La denree dont on veut afficher les détails.
     * @throws StockException
     * Un problème c'est produit lors de l'accès aux données.
     * */
	//Apres chaque test onb detruit la variable
	@After
	public void teanDown() throws Exception{
		stock = null;
	}	

    /**
     * Test la recuperation d'une denree depuis son id
     * @throws Exception
     * */
	@Test
	public void testDenree() throws Exception{
		final long id = 2;
		final String nom = "OrangiFruits";
		final int quantite = 10;
		final String categorie = "Boisson";
		Denree resultat = stock.getDenree(id);

		assertEquals("Erreur lors de la recuperation de la denree : " + id, id, resultat.getId());
		assertEquals("Erreur dans le nom : " + id, nom, resultat.getNom());
		assertEquals("Erreur dans le quantite : " + id, quantite, resultat.getQuantite());
		assertEquals("Erreur dans le categorie : " + id, categorie, resultat.getCategorie());
	}

    /**
     * Test de recuperation une liste de denree en fonction des parametre de posion et nbItem par default
     * @throws Exception
     * */
	//
	@Test
	public void testDenrees() throws Exception{
		final long position = 2;
		final long nombreitems = 2;

		LinkedList<Denree> mesDenrees = new LinkedList<Denree>(); 

    	Denree denree = new Denree();
        denree.setId(2);
        denree.setNom("OrangiFruits");
        denree.setQuantite(10);
        denree.setCategorie("Boisson");
        mesDenrees.add(denree);

    	denree = new Denree();
        denree.setId(3);
        denree.setNom("Slurm");
        denree.setQuantite(10);
        denree.setCategorie("Boisson");
        mesDenrees.add(denree);

		Collection<Denree> resultat = stock.getListeDenree(position, nombreitems);
		Iterator<Denree> it = resultat.iterator();
		Iterator<Denree> itAttendu = mesDenrees.iterator();
		assertEquals("Erreur lors de la recuperation de la liste commencent a " + position + " et contenant " + nombreitems + " ", nombreitems, resultat.size());

	    while (it.hasNext()) {
	    	Denree p = it.next();
	    	Denree pAttendu = itAttendu.next();
	    	System.out.println(p.getNom());
			assertEquals("Erreur lors de la recuperation de la denree : " + pAttendu.getId(), pAttendu.getId(), p.getId());
			assertEquals("Erreur dans le nom : " + pAttendu.getId(), pAttendu.getNom(), p.getNom());
			assertEquals("Erreur dans le quantite : " + pAttendu.getId(), pAttendu.getQuantite(), p.getQuantite());
			assertEquals("Erreur dans le categorie : " + pAttendu.getId(), pAttendu.getCategorie(), p.getCategorie());
		}
	}

    /**
     * Test l'ajout d'une denree
     * @throws Exception
     * */
	@Test
	public void testAjouterDenree() throws Exception{
    	Denree denree = new Denree();    	
        denree.setNom("Test");
        denree.setQuantite(10);
        denree.setCategorie("Test");
		stock.ajouterDenree(denree);

		//Non finaliser a ta demande, voici le principe en retestent avec le numero 13 qui sera identique a ce que l'on insert:
		Denree retour = new Denree();   
		retour = stock.getDenree(13);

		assertEquals("Erreur lors de l'insertion : " + denree.getId(), denree.getNom(), retour.getNom());
	}

    /**
     * Test de la mise a jour d'une denree avec des valeur par default
     * @throws Exception
     * */
	@Test
	public void testMiseAJourDenree() throws Exception{
    	Denree denree = new Denree(); 
    	denree.setId(12);
        denree.setNom("Test2");
        denree.setQuantite(10);
        denree.setCategorie("Test2");
		stock.mAjDenree(denree);

		Denree retour = new Denree();   
		retour = stock.getDenree(12);

		assertEquals("Erreur lors de l'insertion : " + denree.getId(), denree.getNom(), retour.getNom());
	}

    /**
     * Test la recherche de denree
     * @throws Exception
     * */
	@Test
	public void testDenreeRecherche() throws Exception{
		
		LinkedList<Denree> mesDenrees = new LinkedList<Denree>(); 

		final String position = "OrangiFruits";

    	Denree denree = new Denree();
        denree.setId(2);
        denree.setNom("OrangiFruits");
        denree.setQuantite(10);
        denree.setCategorie("Boisson");
        mesDenrees.add(denree);

		Denree retour = new Denree();   
		Collection<Denree> resultat = stock.getDenreeRecherche(position);
		assertEquals("Erreur lors de la recuperation de la liste avec le parmaetre ", 1, resultat.size());
		Iterator<Denree> it = resultat.iterator();
		Iterator<Denree> itAttendu = mesDenrees.iterator();

	    while (it.hasNext()) {
	    	Denree p = it.next();
	    	Denree pAttendu = itAttendu.next();
	    	System.out.println(p.getNom());
			assertEquals("Erreur lors de la recuperation de la denree : " + pAttendu.getId(), pAttendu.getId(), p.getId());
			assertEquals("Erreur dans le nom : " + pAttendu.getId(), pAttendu.getNom(), p.getNom());
			assertEquals("Erreur dans le quantite : " + pAttendu.getId(), pAttendu.getQuantite(), p.getQuantite());
			assertEquals("Erreur dans le categorie : " + pAttendu.getId(), pAttendu.getCategorie(), p.getCategorie());
		}
	}


}