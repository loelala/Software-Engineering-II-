package edu.hm.wedoit.dao;

import edu.hm.wedoit.model.Vendor;

import java.util.List;

/**
 * Created by B3rni on 24.10.2015.
 */
public interface VendorDao
{
    public Vendor getVendorById(String id);

    public List<Vendor> getAllVendor();
}
