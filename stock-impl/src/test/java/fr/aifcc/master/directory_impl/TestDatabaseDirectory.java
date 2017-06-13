package fr.aifcc.master.directory_impl;

import java.util.Collection;
import fr.aifcc.master.directory_api.Person;
import fr.aifcc.master.directory_api.DirectoryObject;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.LinkedList;
import java.util.Iterator;

public class TestDatabaseDirectory
{
	protected DatabaseDirectory directory;
	protected final String driver = "org.apache.derby.jdbc.ClientDriver";
	protected final String url = "jdbc:derby://localhost:1527/NomBDD";


	//Avant chaque test on cree une varibale de test
	@Before
	public void setUp() throws Exception{
		this.directory = new DatabaseDirectory(driver, url);
	}

	//Apres chaque test onb detruit la variable
	@After
	public void teanDown() throws Exception{
		directory = null;
	}	

	@Test
	public void testPerson() throws Exception{
		final long id = 2;
		final String prenom = "Tony";
		final String nom = "Stark";
		Person resultat = directory.getPerson(id);

		assertEquals("Erreur lors de la recuperation de la personne : " + id, id, resultat.getId());
		assertEquals("Erreur dans le prenom : " + id, prenom, resultat.getFirstName());
		assertEquals("Erreur dans le nom : " + id, nom, resultat.getLastName());
	}

	@Test
	public void testPersons() throws Exception{
		final long offset = 2;
		final long limit = 5;

		LinkedList<Person> mesPersons = new LinkedList<Person>(); 

    	Person person = new Person();
        person.setId(2);
        person.setFirstName("Tony");
        person.setLastName("Stark");
        mesPersons.add(person);

        person = new Person();
        person.setId(3);
        person.setFirstName("Bruce");
        person.setLastName("Wayne");
        mesPersons.add(person);

        person = new Person();
        person.setId(4);
        person.setFirstName("James");
        person.setLastName("Kirk");
        mesPersons.add(person);

        person = new Person();
        person.setId(5);
        person.setFirstName("Luke");
        person.setLastName("Skywalker");
        mesPersons.add(person);

        person = new Person();
        person.setId(6);
        person.setFirstName("Nick");
        person.setLastName("Fury");
        mesPersons.add(person);

		Collection<Person> resultat = directory.getPersons(offset, limit);
		Iterator<Person> it = resultat.iterator();
		Iterator<Person> itAttendu = mesPersons.iterator();
		assertEquals("Erreur lors de la recuperation de la liste commencent a " + offset + " et contenant " + limit + " ", limit, resultat.size());

	    while (it.hasNext()) {
	    	Person p = it.next();
	    	Person pAttendu = itAttendu.next();
	    	System.out.println(p.getFirstName());
			assertEquals("Erreur lors de la recuperation de la personne : " + pAttendu.getId(), pAttendu.getId(), p.getId());
			assertEquals("Erreur dans le prenom : " + pAttendu.getId(), pAttendu.getFirstName(), p.getFirstName());
			assertEquals("Erreur dans le nom : " + pAttendu.getId(), pAttendu.getLastName(), p.getLastName());
		}
	}

}