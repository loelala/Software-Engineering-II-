package edu.hm.wedoit.dao.impl;

import edu.hm.wedoit.dao.SupplierDao;
import edu.hm.wedoit.mapper.SupplierMapper;
import edu.hm.wedoit.model.Supplier;

import java.util.List;

/**
 * Created by B3rni on 24.10.2015.
 */
public class SupplierDaoImpl extends AbstractDao implements SupplierDao
{


    @Override
    public Supplier getSupplierById(String id)
    {
        System.out.println("getSupplierById");
        final String query = "SELECT NAME1, LIFNR FROM LFA1 WHERE LIFNR like " + id;

        List<Supplier> supplierList = jdbcTemplate.query(query,new SupplierMapper());
        return supplierList.get(0);
    }

    @Override
    public List<Supplier> getAllSuppliers()
    {
        System.out.println("getAllSuppliers");
        final String query = "SELECT NAME1, LIFNR FROM lfa1;";

        List<Supplier> supplierList = jdbcTemplate.query(query,new SupplierMapper());
        return supplierList;
    }



}
