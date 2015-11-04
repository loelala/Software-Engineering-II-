package edu.hm.wedoit.dao.impl;


import edu.hm.wedoit.dao.OrderDao;
import edu.hm.wedoit.dao.SupplierDao;
import edu.hm.wedoit.dao.SupplierWithScoreDao;
import edu.hm.wedoit.mapper.SupplierMapper;
import edu.hm.wedoit.mapper.SupplierWithScoreMapper;
import edu.hm.wedoit.model.Supplier;
import edu.hm.wedoit.model.SupplierWithScore;
import edu.hm.wedoit.rest.SupplierController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joncn on 02.11.2015.
 */
public class SupplierWithScoreDaoImpl extends AbstractDao implements SupplierWithScoreDao
{
    @Autowired
    OrderDao orderDao;

    @Override
    public List<SupplierWithScore> getAllSuppliersWithScore()
    {
        System.out.println("getAllSuppliersWithScore");
        final String query = "SELECT NAME1, LIFNR FROM lfa1;";

        List<SupplierWithScore> supplierWithScores  = jdbcTemplate.query(query,new SupplierWithScoreMapper(orderDao));

        /*
        for (SupplierWithScore s : supplierWithScores)
        {
            s.setOrders(orderDao.getAllOrdersForId(s.getId()));
        }
        */

        return supplierWithScores;
    }
}
