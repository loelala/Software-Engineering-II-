package edu.hm.wedoit.CallbackHandler;

import edu.hm.wedoit.model.Order;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Joncn on 10.11.2015.
 */
public class EbelnRowCallbackHandler implements RowCallbackHandler
{
    private Map<String, Map<String, Order>> map;

    public EbelnRowCallbackHandler(Map<String, Map<String, Order>> map)
    {
        this.map = map;
    }


    @Override
    public void processRow(ResultSet resultSet) throws SQLException
    {
        while(resultSet.next())
        {
            String ebeln = resultSet.getString("EBELN");
            String lifnr = resultSet.getString("LIFNR");
            if(map.containsKey(lifnr))
            {
                map.get(resultSet.getString("LIFNR")).put(ebeln, new Order(ebeln));
            }
        }
    }
}
