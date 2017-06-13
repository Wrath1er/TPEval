package fr.aifcc.master.stock_api;

public class StockException extends Exception //Il s'agit de tout les exception pouvent etre generer
{
        /**
     * Crée une nouvelle instance de DirectoryException
     * */
    public StockException() {}

    /**
     * Crée une nouvelle instance de DirectoryException
     * @param s
     * Le message détaillant exception
     * */
    public StockException(String s) {
        super(s);
    }

    /**
     * Crée une nouvelle instance de DirectoryException
     * @param t
     * L'exception à l'origine de cette exception
     * */
    public StockException(Throwable t) {
        super(t);
    }

    /**
     * Crée une nouvelle instance de DirectoryException
     * @param s
     * Le message détaillant exception
     * @param t
     * L'exception à l'origine de cette exception
     * */
    public StockException(String s, Throwable t) {
        super(s, t);
    }
}