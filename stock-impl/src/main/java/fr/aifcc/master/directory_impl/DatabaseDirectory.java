package fr.aifcc.master.directory_impl;

import fr.aifcc.master.directory_api.*;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

public class DatabaseDirectory implements Directory
{

    private final Connection connection;

    private final String table = "Person";
    private final String nomId = "id";

    public DatabaseDirectory(String driverClass, String databaseURL) throws DirectoryException {

        try {
            Class.forName(driverClass);
            this.connection = DriverManager.getConnection(databaseURL);
        } catch (ClassNotFoundException e) {
            throw new DirectoryException("Le driver n'existe pas. //"+e.getMessage());
        } catch (SQLException e) {
            throw new DirectoryException(e);
        }
    }

    @Override
    public synchronized Collection<Person> getPersons(long offset, long limit) throws DirectoryException {
        ResultSet res;

        try {
            String requete = "Select * from " + this.table + " WHERE " + this.nomId + ">=? FETCH NEXT ? ROW ONLY";
            PreparedStatement p = this.connection.prepareStatement(requete);
            p.setLong(1, offset);
            p.setLong(2, limit);

            res = p.executeQuery();
            int taille = res.getFetchSize();
            if (!res.next()) {
                res.close();
                throw new DirectoryException("La premiere personne de la liste n'existe pas");
            }

            LinkedList<Person> Persons = new LinkedList<Person>(); 


		    do {
		    	Person person = new Person();
	            person.setId(res.getLong(1));
	            person.setFirstName(res.getString(2));
	            person.setLastName(res.getString(3));
	            Persons.add(person);
			} while (res.next());

            return Persons;

        } 
        catch (SQLException e) {
            throw new DirectoryException(e);
        }
    }

    @Override
    public synchronized Person getPerson(long id) throws DirectoryException {
        ResultSet res;

        try {
            String requete = "Select * from " + this.table + " where id = ?";
            PreparedStatement p = this.connection.prepareStatement(requete);
            p.setLong(1, id);

            res = p.executeQuery();
            int taille = res.getFetchSize();
            if (!res.next()) {
                res.close();
                throw new DirectoryException("La personne demandé n'existe pas");
            } else if (taille != 0) {
                res.close();
                throw new DirectoryException("Pas de résultat");
            }

            Person person = new Person();
            person.setId(res.getLong(1));
            person.setFirstName(res.getString(2));
            person.setLastName(res.getString(3));

            res.close();

            return person;

        } catch (SQLException e) {
            throw new DirectoryException(e);
        }
    }

    @Override
    public synchronized void dispose() throws DirectoryException {

        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new DirectoryException(e);
        }
    }


    public synchronized void updatePerson( Person personne ) throws DirectoryException {
        ResultSet res;

        try {
            String requete = "UPDATE " + this.table + " SET lastName = ?, firstName = ? where id = ?";
            PreparedStatement p = this.connection.prepareStatement(requete);
            p.setString(1, personne.getLastName());
            p.setString(2, personne.getFirstName());
            p.setLong(3, personne.getId());

            p.executeUpdate();

        } catch (SQLException e) {
            throw new DirectoryException(e);
        }
    }

    //public synchronized Collection<Relations> getPersonRelations( long personId ) throws DirectoryException {

    //}

    //public synchronized Person getPersonRecherche( String critere ) throws DirectoryException {

    //}

    //public synchronized Person ajouterRelation( long id, long autreId, String label ) throws DirectoryException {
        
    //}

    //public synchronized Collection<Person> getPersonRecherche( String critere ) throws DirectoryException {
        
    //}
}