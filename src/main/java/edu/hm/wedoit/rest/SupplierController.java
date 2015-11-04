package edu.hm.wedoit.rest;

import edu.hm.wedoit.dao.OrderDao;
import edu.hm.wedoit.dao.SupplierDao;
import edu.hm.wedoit.dao.SupplierWithScoreDao;
import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;
import edu.hm.wedoit.model.SupplierWithScore;
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
    SupplierDao supplierDao;

    @Autowired
    SupplierWithScoreDao supplierWithScoreDao;

    @Autowired
    OrderDao orderDao;


    @RequestMapping("/all")
    public List<Supplier> getAllSuppliers()
    {
        System.out.println("getAllSuppliers Rest");
        return supplierDao.getAllSuppliers();
    }

    @RequestMapping("/allwithscore")
    public List<SupplierWithScore> getAllSuppliersWithScore()
    {
        System.out.println("getAllSuppliersWithScore Rest");
        return supplierWithScoreDao.getAllSuppliersWithScore();
    }

    @RequestMapping("/score/{id}")
    public List<Order> getOrdersForId(@PathVariable(value="id") String id)
    {
        System.out.println("test Rest");
        return orderDao.getAllOrdersForId(id);
    }


    @RequestMapping("/:id")
    public Supplier getSupplierById(@PathVariable(value="id") String id)
    {
        System.out.println("getSupplierById Rest, id = " + id);
        return supplierDao.getSupplierById(id);
    }

}
