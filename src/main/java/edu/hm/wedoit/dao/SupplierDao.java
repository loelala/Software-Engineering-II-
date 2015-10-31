package edu.hm.wedoit.dao;

import edu.hm.wedoit.model.Supplier;

import java.util.List;

/**
 * Created by B3rni on 24.10.2015.
 */
public interface SupplierDao
{
    public Supplier getSupplierById(String id);

    public List<Supplier> getAllSuppliers();
}
