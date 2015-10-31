package edu.hm.wedoit.rest;

import edu.hm.wedoit.dao.SupplierDao;
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
    SupplierDao supplierDao;


    @RequestMapping("/all")
    public List<Supplier> getAllSuppliers()
    {
        System.out.println("getAllSuppliers Rest");
        return supplierDao.getAllSuppliers();
    }


    @RequestMapping("/:id")
    public Supplier getSupplierById(@PathVariable(value="id") String id)
    {
        System.out.println("getSupplierById Rest, id = " + id);
        return supplierDao.getSupplierById(id);
    }

}
