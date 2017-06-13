/*
 * Dernière modification : Mercredi 07 juin[06] 2017
 * */
package fr.aifcc.master.stock_bean;

import java.util.Collection;
import javax.faces.bean.*;

import fr.aifcc.master.stock_api.*;

/**
 * Lien entre la partie métier et la page web.
 * @author PIVARD Julien
 */
@ManagedBean( name = "denreesBean" )
@ViewScoped         // Tant qu'on reste sur la même page cette instance n'est pas déchargé de la mémoire
public class DenreesBean
{

    /**
     * L'instance d'application stock bean sera injecté directement par JSF
     * */
    @ManagedProperty( value = "#{stockBean}" )
    private StockBean stockBean;

    /**
     * Le nombre de résultats par page.
     * */
    private long limite = 7;

    /**
     * La première denree des résultats
     * */
    private long position = 1;

    /**
     * Une page suivante est disponible.
     * */
    private boolean pageSuivante = false;

    /**
     * Constructeur sans arguments conforme à la norme java bean
     * */
    public DenreesBean()
    {}

    /**
     * @return L'instance de stock bean
     * */
    public StockBean getStockBean()
    {
        return this.stockBean;
    }

    /**
     * @param stockBean
     * L'instance de la stock bean
     * */
    public void setStockBean( StockBean stockBean )
    {
        this.stockBean = stockBean;
    }

    /**
     * Permet de passer à la page suivante de la liste des denrees.
     * La page sur laquelle on se trouve est stocké en interne.
     * */
    public void goToNextPage()
    {
        this.position = this.position + this.limite;
    }

    /**
     * Permet de passer à la page précédente de la liste des denrees.
     * */
    public void goToPreviousPage()
    {
        this.position = this.position - this.limite;
        if ( this.position <= 0 )
        {
            this.position = 1;
        }
    }

    /**
     * @return Il y a une page précédente.
     * */
    public boolean hasPreviousPage()
    {
        return this.position > 1;
    }

    /**
     * @return Il y a une page suivante.
     * */
    public boolean hasNextPage()
    {
        return this.pageSuivante;
    }

    /**
     * Le nombre de denrees maximum qui sera retourné dépend de la limite de
     * résultats par pages.
     * @return La liste des denrees
     * @throws StockException
     * Une erreur à été levé lors de l'accès aux données.
     * */
    public Collection<Denree> getDenrees() throws StockException
    {
        StockInterface dir = this.stockBean.getStock();
        Collection<Denree> it = dir.getListeDenree( this.position, this.limite );
        this.pageSuivante = it.size() == this.limite;
        return it;
    }

}
