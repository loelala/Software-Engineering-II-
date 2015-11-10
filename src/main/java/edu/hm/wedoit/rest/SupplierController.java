package edu.hm.wedoit.rest;

import edu.hm.wedoit.dao.AllDao;
import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by B3rni on 24.10.2015.
 */
@RestController
@RequestMapping("/api/supplier")
public class SupplierController
{
    @Autowired
    AllDao allDao;

    @RequestMapping("/all")
    public List<Supplier> getAllSuppliersWithScore()
    {
        System.out.println("getAllSuppliers Rest");
        return allDao.getAllSuppliersWithScore();
    }

    @RequestMapping("/{id}")
    public Supplier getSupplierById(@PathVariable(value="id") String id)
    {
        System.out.println("getSupplierById " + id + " Rest");
        return allDao.getSupplierById(id);
    }

    @RequestMapping("/{id}/orders")
    public List<Order> getOrdersForId(@PathVariable(value="id") String id)
    {
        System.out.println("getOrdersForId " + id + " Rest");
        return allDao.getAllOrdersForId(id);
    }
}
