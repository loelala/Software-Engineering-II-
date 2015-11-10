package edu.hm.wedoit.dao;

import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;

import java.util.List;

/**
 * Interface for the access to the database
 */
public interface AllDao
{
    /**
     * Returns a list of all suppliers
     * @return The list of the suppliers
     */
    public List<Supplier> getAllSuppliersWithScore();

    /**
     * Returns a list of orders for one supplier
     * @param id the LIFNR of a supplier
     * @return the list of orders for the supplier or null if the suppliers-id doesn't exist
     */
    public List<Order> getAllOrdersForId(String id);

    /**
     * Returns the supplier with the given LIFNR
     * @param id the LIFNR of the supplier
     * @return the supplier with the given id or null if this supplier doesn't exist
     */
    public Supplier getSupplierById(String id);
}
