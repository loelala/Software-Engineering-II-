package edu.hm.wedoit.mapper;

import edu.hm.wedoit.model.Supplier;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by B3rni on 24.10.2015.
 */
public class SupplierMapper implements RowMapper<Supplier>
{
    @Override
    public Supplier mapRow(ResultSet resultSet, int i) throws SQLException
    {
        Supplier supplier = new Supplier();

        supplier.setId(resultSet.getString("LIFNR"));
        supplier.setName(resultSet.getString("NAME1"));

        return supplier;
    }
}
