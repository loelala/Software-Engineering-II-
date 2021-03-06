package edu.hm.wedoit.callbackhandler;

import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * This CallbackHandler fills the given maps with the LIFNR(ID) and the NAME1(Name) for each supplier
 */
public class SupplierRowCallbackHandler implements RowCallbackHandler
{
    private Map<String, Map<String, Order>> supplierMap;
    private Map<String, Supplier> suppliers;

    public SupplierRowCallbackHandler(Map<String, Supplier> suppliers, Map<String, Map<String, Order>> supplierMap)
    {
        this.supplierMap = supplierMap;
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
            supplierMap.put(s.getId(), new HashMap<String, Order>());
        }
    }
}
