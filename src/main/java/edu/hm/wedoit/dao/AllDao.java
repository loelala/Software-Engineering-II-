package edu.hm.wedoit.dao;

import edu.hm.wedoit.model.Classification;
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
    /**
     * Enum for the connection to the database
     */
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

    /**
     * Returns a list of all suppliers with filtered orders.
     * Only orders between the given date-range are processed
     * @param from The beginning of the date-range
     * @param to The ending of the date-range
     * @return The list of all supplieres with their orders between the date-range
     */
    List<Supplier> getAllSuppliersDate(Date from, Date to);

    List<Supplier> getAllSuppliersClassificationDate(Classification classification, Date fromDate, Date toDate);

    /**
     * A blocking method for returning the current state of the database-connection
     * Can be used for long polling, to check the db-connection
     * @return the State-Enum representing the current connection state
     */
    State getState();

    void addObserver(Observer o);

    void deleteObserver(Observer o);
}
