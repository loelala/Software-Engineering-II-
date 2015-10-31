package edu.hm.wedoit.mapper;

import edu.hm.wedoit.model.SupplierWithScore;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Joncn on 31.10.2015.
 */
public class SupplierWithScoreMapper extends SupplierMapper {

    @Override
    public SupplierWithScore mapRow(ResultSet resultSet, int i) throws SQLException
    {
        SupplierWithScore supplier = new SupplierWithScore();

        supplier.setId(resultSet.getString("LIFNR"));
        supplier.setName(resultSet.getString("NAME1"));

        return supplier;
    }
}
