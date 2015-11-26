package edu.hm.wedoit.callbackhandler;

import edu.hm.wedoit.model.Order;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * This CallbackHandler fills the given map with the EBELN(Order ID) and the BLDAT(Delivery Date) for each supplier
 */
public class BldatRowCallbackHandler implements RowCallbackHandler
{
    private Map<String, Map<String, Order>> supplierMap;

    public BldatRowCallbackHandler(Map<String, Map<String, Order>> map)
    {
        this.supplierMap = map;
    }

    @Override
    public void processRow(ResultSet resultSet) throws SQLException
    {
        while(resultSet.next())
        {
            String ebeln = resultSet.getString("EBELN");
            for(Map<String, Order> m : supplierMap.values())
            {
                if(m.containsKey(ebeln))
                {
                    m.get(ebeln).setDeliveryDate(resultSet.getDate("BLDAT"));
                }
            }
        }
    }
}
