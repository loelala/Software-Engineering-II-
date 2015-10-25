package edu.hm.wedoit.dao.impl;

import edu.hm.wedoit.dao.VendorDao;
import edu.hm.wedoit.mapper.VendorMapper;
import edu.hm.wedoit.model.Vendor;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by B3rni on 24.10.2015.
 */
public class VendorDaoImpl extends AbstractDao implements VendorDao
{


    @Override
    public Vendor getVendorById(String id)
    {
        final String query = "SELECT NAME1 FROM LFA1";



        return null;
    }

    @Override
    public List<Vendor> getAllVendor()
    {
        System.out.println("getAllVendor");
        final String query = "SELECT NAME1, LIFNR FROM lfa1;";

        List<Vendor> vendorList = jdbcTemplate.query(query,new VendorMapper());
        return vendorList;
    }



}
