package edu.hm.wedoit.dao.impl;


import edu.hm.wedoit.CallbackHandler.BldatRowCallbackHandler;
import edu.hm.wedoit.CallbackHandler.EbelnRowCallbackHandler;
import edu.hm.wedoit.CallbackHandler.SlfdtRowCallbackHandler;
import edu.hm.wedoit.CallbackHandler.SupplierRowCallbackHandler;
import edu.hm.wedoit.dao.AllDao;
import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;

import java.util.*;

/**
 * Implemetation of the {@link edu.hm.wedoit.dao.AllDao} interface
 */
public class AllDaoImpl extends AbstractDao implements AllDao
{
    private Map<String, Supplier> supplierList;
    private Map<String, Map<String, Order>> supplierMap;
    private long cacheTime = 0;

    /**
     * This method retrieves the data from the database and fills the supplierList and the supplierMap.
     * This is only done, if the cacheTime is older then 5 minutes.
     * It is Thread-safe. Due to that only one cache-update is done at once
     */
    private void getDataFromDatabase()
    {
        synchronized (this)
        {
            if (supplierMap == null || (System.currentTimeMillis() - cacheTime) > 60000 * 5)
            {
                System.out.println("Fetching suppliers and their orders");

                supplierMap = new HashMap<>();

                supplierList = new HashMap<>();
                final String supplierQuery = "SELECT NAME1, LIFNR FROM sap_emulation.lfa1;";
                jdbcTemplate.query(supplierQuery, new SupplierRowCallbackHandler(supplierList, supplierMap));

                final String ebelnQuery = "SELECT LIFNR, EBELN FROM sap_emulation.ekko;";
                jdbcTemplate.query(ebelnQuery, new EbelnRowCallbackHandler(supplierMap));

                final String bldatQuery = "SELECT EBELN, BLDAT FROM sap_emulation.ekbe WHERE BEWTP = 'E' AND BWART = 101;";
                jdbcTemplate.query(bldatQuery, new BldatRowCallbackHandler(supplierMap));

                final String slfdtQuery = "SELECT EBELN, SLFDT FROM eket";
                jdbcTemplate.query(slfdtQuery, new SlfdtRowCallbackHandler(supplierMap));

                for (Supplier s : supplierList.values())
                {
                    List orders;
                    Collection<Order> o = supplierMap.get(s.getId()).values();
                    if (o instanceof List)
                    {
                        orders = (List) o;
                    }
                    else
                    {
                        orders = new ArrayList(o);
                    }

                    s.setOrders(orders);
                }

                cacheTime = System.currentTimeMillis();
            }
        }
    }

    /**
     * {@link AllDao#getAllSuppliersWithScore()}
     */
    @Override
    public List<Supplier> getAllSuppliersWithScore()
    {
        System.out.println("getAllSuppliersWithScore called");
        getDataFromDatabase();
        List<Supplier> suppliers;
        Collection<Supplier> s = supplierList.values();
        if (s instanceof List)
        {
            suppliers = (List) s;
        }
        else
        {
            suppliers = new ArrayList(s);
        }

        Collections.sort(suppliers, new Comparator<Supplier>()
        {
            @Override
            public int compare(Supplier o1, Supplier o2)
            {
                return Double.compare(o1.getScore(), o2.getScore()) * (-1);
            }
        });
        return suppliers;
    }


    /**
     * {@link AllDao#getAllOrdersForId(String)}
     */
    @Override
    public List<Order> getAllOrdersForId(String id)
    {
        System.out.println("getAllOrdersForId " + id + " called");
        getDataFromDatabase();
        if(supplierMap.containsKey(id))
        {
            List<Order> orders;
            Collection<Order> o = supplierMap.get(id).values();
            if (o instanceof List)
            {
                orders = (List<Order>) o;
            }
            else
            {
                orders = new ArrayList<>(o);
            }
            return orders;
        }
        return null;
    }

    /**
     * {@link AllDao#getSupplierById(String)}
     */
    @Override
    public Supplier getSupplierById(String id)
    {
        System.out.println("getSupplierById " + id + " called");
        getDataFromDatabase();
        if(supplierList.containsKey(id))
        {
            return supplierList.get(id);
        }
        return null;
    }
}
