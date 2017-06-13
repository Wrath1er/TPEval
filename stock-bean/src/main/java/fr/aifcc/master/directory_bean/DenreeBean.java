/*
 * Dernière modification : Mercredi 07 juin[06] 2017
 * */
package fr.aifcc.master.stock_bean;

import java.util.Collection;
import java.util.Iterator;
import javax.faces.bean.*;

import fr.aifcc.master.stock_api.*;

/**
 * Lien entre la partie métier et la page web.
 * @author PIVARD Julien
 */
@ManagedBean( name = "denreeBean" )
@ViewScoped
public class DenreeBean
{

    /**
     * L'identifiant de la denree dont le détail sera affiché sur la page.
     * */
    private long id;

    /**
     * L'instance de stock bean qui est instancié par JSF
     * */
    @ManagedProperty( value = "#{stockBean}" )
    private StockBean stockBean;
    private Denree newDenree = new Denree();

    /**
     * Une instance de denree vide
     * */
    private Denree denree = new Denree();

    /**
     * La chaine de la recherche faites par l'utilisateur.
     * On le garde en mémoire pour pouvoir l'afficher après le
     * rechargement de la page dans le champs de recherche.
     * */
    private String chaineRecherche = "";

    /**
     * Le résultat de la recherche.
     * */
    private Collection<Denree> resultatRecherche = null;

    /**
     * @return L'instance de stock bean
     * */
    public StockBean getStockBean()
    {
        return this.stockBean;
    }

    /**
     * @param stockBean
     * Une instance de stock bean
     * */
    public void setStockBean( StockBean stockBean )
    {
        this.stockBean = stockBean;
    }

    /**
     * @return L'identifiant de la denree.
     * */
    public long getId()
    {
        return this.id;
    }

    /**
     * @param id
     * L'identifiant de la denree qui sera affiché sur la page.
     * Cette identifiant doit être donné avant le traitement de la page.
     * */
    public void setId( long id )
    {
        this.id = id;
    }

    /**
     * @return La recherche effectué par l'utilisateur.
     * */
    public String getChaineRecherche()
    {
        return this.chaineRecherche;
    }

    /**
     * @param chaine
     * La recherche que fait l'utilisateur.
     * */
    public void setChaineRecherche( String chaine )
    {
        this.chaineRecherche = chaine;
    }

    /**
     * @return La denree dont on veut afficher les détails.
     * @throws StockException
     * Un problème c'est produit lors de l'accès aux données.
     * */
    public Denree getDenree() throws StockException
    {
        if( this.denree.getId() == 0 )
        {
            StockInterface dir = this.stockBean.getStock();
            this.denree = dir.getDenree( this.id );
        }
        return this.denree;
    }

    /**
     * La mise à jour de la denree se fait grâce à l'instance de Denree
     * qui est stocké.
     * @throws StockException
     * Un problème c'est produit lors de la mise à jours des données.
     * */
    public void majDenree() throws StockException
    {
        StockInterface dir = this.stockBean.getStock();
        dir.mAjDenree( this.denree );
        this.denree = dir.getDenree( this.id );
    }

    /**
     * Permet d'exécuter la recherche demandé par l'utilisateur.
     * @throws StockException
     * Un problème c'est produit lors de la récupération des données.
     * */
    public void faireRecherche() throws StockException
    {
        this.resultatRecherche = this.stockBean.getStock().getDenreeRecherche( this.chaineRecherche );
    }

    /**
     * Ajouter une denrer
     * @throws StockException
     * Un problème c'est produit lors de l'insert des données.
     * */
    public void ajoutDenrer() throws StockException
    {
        StockInterface dir = this.stockBean.getStock();
        dir.ajouterDenree( this.newDenree );
    }

    /**
     * @return La liste des denrees qui correspondent aux résultats de recherche.
     * */
    public Collection<Denree> getResultatRecherche()
    {
        // On retire la denree qui fait la recherche de la liste de résultats de recherche
        if ( this.resultatRecherche != null )
        {

            Collection<Denree> listeResultat = this.resultatRecherche;
            Iterator<Denree> it = listeResultat.iterator();
            while ( it.hasNext() )
            {
                Denree p = it.next();
                //if ( p.getId() == this.id )
                //{
                //    it.remove();
                //}
            }

        }
        return this.resultatRecherche;
    }

    /**
     * @return Le nombre de résultats dans la recherche.
     * */
    public int getTailleResultat()
    {
        if ( this.resultatRecherche == null )
        {
            return -1;
        }
        return this.resultatRecherche.size();
    }

    /**
     * @return La denree que l'on veux ajouter
     * */
    public Denree getNewDenree()
    {
        return this.newDenree;
    }

    /**
     * @return inserer en objet la denree que l'on veux inserer en bdd
     * */
    public void setNewDenree(Denree denree)
    {
        this.newDenree = denree;
    }
}
