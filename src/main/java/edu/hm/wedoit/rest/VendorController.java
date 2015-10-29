package edu.hm.wedoit.rest;

import edu.hm.wedoit.dao.VendorDao;
import edu.hm.wedoit.model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by B3rni on 24.10.2015.
 */
@RestController
@RequestMapping("/api/vendor")
public class VendorController
{

    @Autowired
    VendorDao vendorDao;


    @RequestMapping("/all")
    public List<Vendor> getAllVendor()
    {
        System.out.println("getAllVendor Rest");
        return vendorDao.getAllVendor();
    }


    @RequestMapping("/:id")
    public Vendor getVendorById(@PathVariable(value="id") String id)
    {
        System.out.println("id" + id);
        Vendor v = new Vendor();
        v.setName("der Vendor");
        v.setId("12354");
        return v;
    }

}
