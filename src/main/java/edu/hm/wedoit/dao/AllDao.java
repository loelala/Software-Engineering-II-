package edu.hm.wedoit.dao;

import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;

import java.sql.Date;
import java.util.List;
import java.util.Observer;

/**
 * Interface for the access to the database
 */
public interface AllDao
{
    enum State
    {
        CONNECTED, CACHE_ONLY, NO_CONNECTION_AND_CACHE;
    }

    /**
     * Returns a list of all suppliers
     * @return The list of the suppliers
     */
    List<Supplier> getAllSuppliersWithScore();

    /**
     * Returns a list of orders for one supplier
     * @param id the LIFNR of a supplier
     * @return the list of orders for the supplier or null if the suppliers-id doesn't exist
     */
    List<Order> getAllOrdersForId(String id);

    /**
     * Returns the supplier with the given LIFNR
     * @param id the LIFNR of the supplier
     * @return the supplier with the given id or null if this supplier doesn't exist
     */
    Supplier getSupplierById(String id);

    List<Supplier> getAllSuppliersDate(Date from, Date to);

    State getState();

     void addObserver(Observer o);

    void deleteObserver(Observer o);
}
