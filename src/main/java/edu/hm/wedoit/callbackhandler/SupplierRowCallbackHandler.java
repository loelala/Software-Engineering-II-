package edu.hm.wedoit.callbackhandler;

import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joncn on 10.11.2015.
 */
public class SupplierRowCallbackHandler implements RowCallbackHandler
{

    private Map<String, Map<String, Order>> map;
    private Map<String, Supplier> suppliers;

    public SupplierRowCallbackHandler(Map<String, Supplier> suppliers, Map<String, Map<String, Order>> map)
    {
        this.map = map;
        this.suppliers = suppliers;
    }

    @Override
    public void processRow(ResultSet resultSet) throws SQLException
    {
        while(resultSet.next())
        {
            Supplier s = new Supplier();

            String lifnr = resultSet.getString("LIFNR");
            s.setId(lifnr);
            s.setName(resultSet.getString("NAME1").trim());

            suppliers.put(lifnr, s);
            map.put(s.getId(), new HashMap<String, Order>());
        }
    }
}
