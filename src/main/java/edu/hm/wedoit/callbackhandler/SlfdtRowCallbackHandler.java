package edu.hm.wedoit.callbackhandler;

import edu.hm.wedoit.model.Order;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * This CallbackHandler fills the given map with the EBELN(Order ID) and the SLFDT(Promised Date) for each supplier
 */
public class SlfdtRowCallbackHandler implements RowCallbackHandler
{
    private Map<String, Map<String, Order>> supplierMap;

    public SlfdtRowCallbackHandler(Map<String, Map<String, Order>> map)
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
                    m.get(ebeln).setPromisedDate(resultSet.getDate("SLFDT"));
                }
            }
        }
    }
}
