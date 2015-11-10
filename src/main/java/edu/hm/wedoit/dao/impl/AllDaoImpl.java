package edu.hm.wedoit.dao.impl;


import edu.hm.wedoit.CallbackHandler.BldatRowCallbackHandler;
import edu.hm.wedoit.CallbackHandler.EbelnRowCallbackHandler;
import edu.hm.wedoit.CallbackHandler.SlfdtRowCallbackHandler;
import edu.hm.wedoit.CallbackHandler.SupplierRowCallbackHandler;
import edu.hm.wedoit.dao.AllDao;
import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;

import java.sql.*;
import java.sql.Date;
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
     * This is only done, if the cacheTime is older then 5 minutes or if forceUpdate is set to true
     * It is Thread-safe. Due to that only one cache-update is done at once
     */
    private void getDataFromDatabase(boolean forceUpdate)
    {
        synchronized (this)
        {
            if (forceUpdate || supplierMap == null || (System.currentTimeMillis() - cacheTime) > 60000 * 5)
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
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            Thread.sleep(1000 * 60 * 1);
                            System.out.println("Updating cache");
                            getDataFromDatabase(true);
                            System.out.println("Cache is now up to date");
                        }
                        catch (InterruptedException e)
                        {
                            System.out.println("UpdateThread was interrupted");
                        }
                    }
                }).start();
            }
        }
    }

    /**
     * {@link AllDao#getAllSuppliersWithScore()}
     */
    @Override
    public List<Supplier> getAllSuppliersWithScore()
    {
        System.out.println("getAllSuppliers called");
        getDataFromDatabase(false);
        List<Supplier> suppliers;
        synchronized (this)
        {
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
        }
        return suppliers;
    }


    /**
     * {@link AllDao#getAllOrdersForId(String)}
     */
    @Override
    public List<Order> getAllOrdersForId(String id)
    {
        System.out.println("getAllOrdersForId " + id + " called");
        getDataFromDatabase(false);
        synchronized (this)
        {
            if (supplierMap.containsKey(id))
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
        getDataFromDatabase(false);
        synchronized (this)
        {
            if (supplierList.containsKey(id))
            {
                return supplierList.get(id);
            }
        }
        return null;
    }

    @Override
    public List<Supplier> getAllSuppliersDate(java.sql.Date from, java.sql.Date to)
    {
        System.out.println("getAllSuppliersDate from " + from + " to " + to + " called");
        getDataFromDatabase(false);
        Map<String, Supplier> supplierListDate = new HashMap<>();
        Map<String, Map<String, Order>> supplierMapDate= new HashMap<>();
        synchronized (this)
        {
            for (String supplierID : supplierMap.keySet())
            {
                Map<String, Order> orders = supplierMap.get(supplierID);
                Map<String, Order> newOrders = new HashMap<>();
                for (String orderID : orders.keySet())
                {
                    Order order = orders.get(orderID);
                    if (order == null || order.getDeliveryDate() == null || order.getPromisedDate() == null)
                    {
                        continue;
                    }
                    if (order.getDeliveryDate().after(from) && order.getDeliveryDate().before(to)
                            || order.getPromisedDate().after(from) && order.getPromisedDate().before(to))
                    {
                        Order newOrder = new Order(order);
                        newOrders.put(orderID, newOrder);
                    }
                }
                supplierMapDate.put(supplierID, newOrders);
            }

            for (Supplier s : supplierList.values())
            {
                Supplier newSupplier = new Supplier();
                newSupplier.setName(s.getName());
                newSupplier.setId(s.getId());
                supplierListDate.put(s.getId(), newSupplier);
            }
        }

        for (Supplier s : supplierListDate.values())
        {
            List orders;
            Collection<Order> o = supplierMapDate.get(s.getId()).values();
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

        List<Supplier> suppliers;
        Collection<Supplier> s = supplierListDate.values();
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
}
