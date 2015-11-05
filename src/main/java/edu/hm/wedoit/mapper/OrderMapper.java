package edu.hm.wedoit.mapper;

import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Joncn on 02.11.2015.
 */
public class OrderMapper implements RowMapper<Order>
{
    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException
    {
        Order order = new Order();

        order.setDeliveryDate(resultSet.getDate("BLDAT"));
        order.setPromisedDate(resultSet.getDate("SLFDT"));
        order.setEbeln(resultSet.getString("EBELN"));

        return order;
    }
}
