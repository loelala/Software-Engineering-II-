package edu.hm.wedoit.dao;

import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;

import java.util.List;

/**
 * Created by Joncn on 02.11.2015.
 */
public interface AllDao
{
    public List<Supplier> getAllSuppliersWithScore();

    public List<Order> getAllOrdersForId(String id);

    public Supplier getSupplierById(String id);
}
