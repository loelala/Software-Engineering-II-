package edu.hm.wedoit.callbackhandler;

import edu.hm.wedoit.model.Order;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * This CallbackHandler fills the given map with the EBELN(Order ID) and the LIFNR(ID) for each supplier
 */
public class EbelnRowCallbackHandler implements RowCallbackHandler
{
    private Map<String, Map<String, Order>> supplierMap;

    public EbelnRowCallbackHandler(Map<String, Map<String, Order>> map)
    {
        this.supplierMap = map;
    }


    @Override
    public void processRow(ResultSet resultSet) throws SQLException
    {
        while(resultSet.next())
        {
            String ebeln = resultSet.getString("EBELN");
            String lifnr = resultSet.getString("LIFNR");
            if(supplierMap.containsKey(lifnr))
            {
                supplierMap.get(resultSet.getString("LIFNR")).put(ebeln, new Order(ebeln));
            }
        }
    }
}
