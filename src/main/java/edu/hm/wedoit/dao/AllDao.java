package edu.hm.wedoit.dao;

import edu.hm.wedoit.model.enums.Classification;
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

    void renewCache();

    /**
     * Returns a list of all suppliers
     * @return The list of the suppliers
     */
    List<Supplier> getAllSuppliers();

    /**
     * Returns a list of all suppliers of the given classification
     * @param classification the classification to filter for
     * @return the list of all suppliers of the given classification
     */
    List<Supplier> getAllSuppliersClassification(Classification classification);

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

    /**
     * Returns a list of all suppliers with given classification and filtered orders.
     * Only orders between the given date-range are processed, for suppliers with the
     * given classification
     * @param classification the classification to filter for
     * @param fromDate The beginning of the date-range
     * @param toDate The ending of the date-range
     * @return
     */
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
