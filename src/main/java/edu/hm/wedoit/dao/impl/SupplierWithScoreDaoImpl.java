package edu.hm.wedoit.dao.impl;


import edu.hm.wedoit.dao.AllDao;
import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;


import java.sql.Date;
import java.util.List;

/**
 * Created by Joncn on 02.11.2015.
 */
public class SupplierWithScoreDaoImpl extends AbstractDao implements AllDao
{

    @Override
    public List<Supplier> getAllSuppliersWithScore() {
        return null;
    }

    @Override
    public List<Order> getAllOrdersForId(String id) {
        return null;
    }

    @Override
    public Supplier getSupplierById(String id) {
        return null;
    }

    @Override
    public List<Supplier> getAllSuppliersDate(Date from, Date to) {
        return null;
    }
}
