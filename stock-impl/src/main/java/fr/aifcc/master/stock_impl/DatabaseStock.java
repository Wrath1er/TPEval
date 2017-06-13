package fr.aifcc.master.stock_impl;

import fr.aifcc.master.stock_api.*;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

public class DatabaseStock implements StockInterface
{

    private final Connection connection;

    private final String table = "Denree";
    private final String nomId = "id";

    /**
     * permet de ce connecter a la base
     * @param driverClass
     * Le driver a recuperer
     * @param databaseURL
     * databaseURL
     * */
    public DatabaseStock(String driverClass, String databaseURL) throws StockException {

        try {
            Class.forName(driverClass);
            this.connection = DriverManager.getConnection(databaseURL);
        } catch (ClassNotFoundException e) {
            throw new StockException("Le driver n'existe pas. //"+e.getMessage());
        } catch (SQLException e) {
            throw new StockException(e);
        }
    }

    /**
     * retourne une liste de denree en fonction du point de deparer et du nombre que lon en veux
     * @param position
     * id de depart pour reuperer la liste
     * @param nombreitems
     * nombre d'element a recuperer
     * */
    @Override
    public synchronized Collection<Denree> getListeDenree(long position, long nombreitems) throws StockException {
        ResultSet res;

        try {
            String requete = "Select * from " + this.table + " WHERE " + this.nomId + ">=? FETCH NEXT ? ROW ONLY";
            PreparedStatement p = this.connection.prepareStatement(requete);
            p.setLong(1, position);
            p.setLong(2, nombreitems);

            res = p.executeQuery();
            int taille = res.getFetchSize();
            if (!res.next()) {
                res.close();
                throw new StockException("La premiere denree de la liste n'existe pas");
            }

            LinkedList<Denree> Denrees = new LinkedList<Denree>(); 


            do {
                Denree denree = new Denree();
                denree.setId(res.getLong(1));
                denree.setNom(res.getString(2));
                denree.setQuantite(res.getLong(3));
                denree.setCategorie(res.getString(4));
                Denrees.add(denree);
            } while (res.next());

            return Denrees;

        } 
        catch (SQLException e) {
            throw new StockException(e);
        }
    }

    /**
     * permet d'acceder a une denree en fonction de son id
     * @param id
     * l'id de la denree a recuperer
     * */ 
    @Override
    public synchronized Denree getDenree(long id) throws StockException {
        ResultSet res;

        try {
            String requete = "Select * from " + this.table + " where id = ?";
            PreparedStatement p = this.connection.prepareStatement(requete);
            p.setLong(1, id);

            res = p.executeQuery();
            int taille = res.getFetchSize();
            if (!res.next()) {
                res.close();
                throw new StockException("La denree demandé n'existe pas");
            } else if (taille != 0) {
                res.close();
                throw new StockException("Pas de résultat");
            }

            Denree denree = new Denree();
            denree.setId(res.getLong(1));
            denree.setNom(res.getString(2));
            denree.setQuantite(res.getLong(3));
            denree.setCategorie(res.getString(4));

            res.close();

            return denree;

        } catch (SQLException e) {
            throw new StockException(e);
        }
    }

     /**
     * permet de fermer la connection a la bdd
     * */  
    //
    @Override
    public synchronized void dispose() throws StockException {

        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new StockException(e);
        }
    }

    /**
     * fonction de mise a jour d'une denree en fonction de l'id et avec les nouvelle données
     * @param denree
     * La denree a mettre a jour avec c'est nouvelle valeur
     * */ 
    @Override
    public synchronized void mAjDenree( Denree denree ) throws StockException{
        ResultSet res;

        try {
            String requete = "UPDATE " + this.table + " SET nom = ?, quantite = ?, categorie = ? where id = ?";
            PreparedStatement p = this.connection.prepareStatement(requete);
            p.setString(1, denree.getNom());
            p.setLong(2, denree.getQuantite());
            p.setString(3, denree.getCategorie());
            p.setLong(4, denree.getId());

            p.executeUpdate();

        } catch (SQLException e) {
            throw new StockException(e);
        }
    }

    /**
     * permet d'ajouter une denree a la liste
     * @param item
     * la denree a ajouter
     * */
    @Override
    public synchronized void ajouterDenree( Denree item ) throws StockException{
        ResultSet res;

        try {
            String requete = "INSERT INTO " + this.table + " (nom, quantite, categorie) values (?, ?, ?)";
            PreparedStatement p = this.connection.prepareStatement(requete);
            p.setString(1, item.getNom());
            p.setLong(2, item.getQuantite());
            p.setString(3, item.getCategorie());        

            p.executeUpdate();

        } catch (SQLException e) {
            throw new StockException(e);
        }
    }

    /**
     * permet de rechercher une liste de denree en fonction d'une recherche sur le nom
     * @param critere
     * le mot qui servira a rechercher
     * */
    @Override
    public synchronized Collection<Denree> getDenreeRecherche( String critere ) throws StockException{
        try
        {

            ResultSet resultats;

            String requete = "SELECT * FROM " + this.table + " WHERE nom LIKE ? ";
            PreparedStatement requetePrepare = this.connection.prepareStatement( requete );
            requetePrepare.setString( 1, critere );

            resultats = requetePrepare.executeQuery();

            Collection<Denree> Denrees = new LinkedList<>();

            while ( resultats.next() )
            {
                Denrees.add( getDenree( resultats.getLong( 1 ) ) );
            }
            resultats.close();

            return Denrees;

        }
        catch (SQLException e) {
            throw new StockException(e);
        }

    }
}