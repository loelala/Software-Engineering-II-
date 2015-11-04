package edu.hm.wedoit.mapper;

import edu.hm.wedoit.dao.OrderDao;
import edu.hm.wedoit.dao.SupplierWithScoreDao;
import edu.hm.wedoit.dao.impl.OrderDaoImpl;
import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.SupplierWithScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Joncn on 31.10.2015.
 */
public class SupplierWithScoreMapper implements RowMapper<SupplierWithScore> {

    private OrderDao orderDao;

    public SupplierWithScoreMapper(OrderDao orderDao)
    {
        this.orderDao = orderDao;
    }

    public SupplierWithScore mapRow(ResultSet resultSet, int i) throws SQLException
    {
        SupplierWithScore supplier = new SupplierWithScore();

        supplier.setId(resultSet.getString("LIFNR"));
        supplier.setName(resultSet.getString("NAME1"));

        List<Order> orders = orderDao.getAllOrdersForId(supplier.getId());
        supplier.setOrders(orders);

        return supplier;
    }
}
