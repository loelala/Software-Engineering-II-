package edu.hm.wedoit.rest;

import edu.hm.wedoit.dao.AllDao;
import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public List<Supplier> getAllSuppliers()
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

    @RequestMapping("/all/between/{from}/{to}")
    public List<Supplier> getAllSuppliersDate(@PathVariable(value="from") String from, @PathVariable(value="to") String to)
    {
        System.out.println("getAllSuppliersDate Rest");
        DateFormat format = new SimpleDateFormat("ddMMyyyy");
        Date fromDate;
        Date toDate;
        try
        {
            fromDate = new Date(format.parse(from).getTime());
            toDate = new Date(format.parse(to).getTime());
        }
        catch (ParseException e)
        {
            return null;
        }

        return allDao.getAllSuppliersDate(fromDate, toDate);
    }
}
