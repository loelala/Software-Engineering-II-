package edu.hm.wedoit.CallbackHandler;

import edu.hm.wedoit.model.Order;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Joncn on 09.11.2015.
 */
public class OrderRowCallbackHandler implements RowCallbackHandler {


    private Map<String, List<Order>> map;

    public OrderRowCallbackHandler(Map<String, List<Order>> map)
    {
        this.map = map;
    }

    @Override
    public void processRow(ResultSet resultSet) throws SQLException {
        while(resultSet.next())
        {
            Order order = new Order();

            order.setEbeln(resultSet.getString("EBELN"));
            order.setPromisedDate(resultSet.getDate("SLFDT"));
            order.setDeliveryDate(resultSet.getDate("BLDAT"));

            map.get(resultSet.getString("LIFNR")).add(order);
        }
    }
}
