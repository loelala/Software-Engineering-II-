package edu.hm.wedoit.mapper;

import edu.hm.wedoit.model.Vendor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by B3rni on 24.10.2015.
 */
public class VendorMapper implements RowMapper<Vendor>
{
    @Override
    public Vendor mapRow(ResultSet resultSet, int i) throws SQLException
    {
        Vendor vendor = new Vendor();

        vendor.setId(resultSet.getString("LIFNR"));
        vendor.setName(resultSet.getString("NAME1"));

        return vendor;
    }
}
