/*
 * Dernière modification : Mercredi 07 juin[06] 2017
 * */
package fr.aifcc.master.stock_bean;

import javax.faces.bean.*;

import fr.aifcc.master.stock_api.StockInterface;
import fr.aifcc.master.stock_impl.*;

/**
 * Cette classe ne sera jamais instancié manuellement.
 * Toutes ces instanciations seront faites par JSF
 * @author PIVARD Julien
 */
@ManagedBean( name = "stockBean" )
@ApplicationScoped          // L'application est instancié dés que l'app est lancée
public class StockBean
{

    private final StockInterface stock;

    /**
     * Une instance géré par JSF qui donne accès à la BDD.
     * */
    public StockBean()
        throws Exception
    {
        this.stock = new DatabaseStock( "org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/Stock" );
    }

    /**
     * @return L'instance de stock.
     * */
    public StockInterface getStock()
    {
        return this.stock;
    }

}
