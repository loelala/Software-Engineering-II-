package edu.hm.wedoit.dao.impl;


import edu.hm.wedoit.callbackhandler.BldatRowCallbackHandler;
import edu.hm.wedoit.callbackhandler.EbelnRowCallbackHandler;
import edu.hm.wedoit.callbackhandler.SlfdtRowCallbackHandler;
import edu.hm.wedoit.callbackhandler.SupplierRowCallbackHandler;
import edu.hm.wedoit.comparators.ClassificationScoreComparator;
import edu.hm.wedoit.dao.AllDao;
import edu.hm.wedoit.model.enums.Classification;
import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.*;

/**
 * Implemetation of the {@link edu.hm.wedoit.dao.AllDao} interface
 */
public class AllDaoImpl extends AbstractDao implements AllDao
{
    private final static Logger logger = LoggerFactory.getLogger(AllDaoImpl.class);

    private Map<String, Supplier> supplierList;
    private Map<String, Map<String, Order>> supplierMap;
    private long cacheTime = 0;
    private State state = State.CONNECTED;

    /**
     * This method retrieves the data from the database and fills the supplierList and the supplierMap.
     * This is only done, if the cacheTime is older then 5 minutes or if forceUpdate is set to true
     * It is Thread-safe. Due to that only one cache-update is done at once
     */
    private void getDataFromDatabase(boolean forceUpdate)
    {
        logger.debug("getDataFromDatabase({})", forceUpdate);
        if (forceUpdate || supplierMap == null || (System.currentTimeMillis() - cacheTime) > 60000 * 5)
        {
            logger.debug("Fetching suppliers and their orders");

            Map<String, Map<String, Order>> supplierMap = new HashMap<>();
            Map<String, Supplier> supplierList = new HashMap<>();

            //get data from DB and fill the two maps
            try
            {
                final String supplierQuery = "SELECT NAME1, LIFNR FROM sap_emulation.lfa1;";
                jdbcTemplate.query(supplierQuery, new SupplierRowCallbackHandler(supplierList, supplierMap));

                final String ebelnQuery = "SELECT LIFNR, EBELN FROM sap_emulation.ekko;";
                jdbcTemplate.query(ebelnQuery, new EbelnRowCallbackHandler(supplierMap));

                final String bldatQuery = "SELECT EBELN, BLDAT FROM sap_emulation.ekbe WHERE BEWTP = 'E' AND BWART = 101;";
                jdbcTemplate.query(bldatQuery, new BldatRowCallbackHandler(supplierMap));

                final String slfdtQuery = "SELECT EBELN, SLFDT FROM eket";
                jdbcTemplate.query(slfdtQuery, new SlfdtRowCallbackHandler(supplierMap));
            }
            catch (Exception e)
            {
                logger.debug("No Cache and no Database connection");
                state = State.NO_CONNECTION_AND_CACHE;
            }

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
            if(!supplierMap.isEmpty() || !supplierList.isEmpty())
            {
                synchronized (this)
                {
                    this.supplierMap = supplierMap;
                    this.supplierList = supplierList;
                    state = State.CONNECTED;
                }
            }
            else if(this.supplierMap.isEmpty() || this.supplierList.isEmpty())
            {
                logger.debug("No Cache and no Database connection");
                state = State.NO_CONNECTION_AND_CACHE;
            }
            else
            {
                logger.debug("Cache only");
                state = State.CACHE_ONLY;
            }
            setChanged();
            notifyObservers();

            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        Thread.sleep(1000 * 60 * 1);
                        logger.debug("Updating cache");
                        getDataFromDatabase(true);
                    }
                    catch (InterruptedException e)
                    {
                        logger.debug("Updating cache was interrupted");
                    }
                }
            }).start();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Supplier> getAllSuppliers()
    {
        logger.debug("getAllSuppliersWithScore()");
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

            Collections.sort(suppliers, new ClassificationScoreComparator());

        }
        return suppliers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Supplier> getAllSuppliersClassification(Classification classification)
    {
        List<Supplier> oldSuppliers = getAllSuppliers();
        List<Supplier> filteredSuppliers = new ArrayList<>();
        for(Supplier s : oldSuppliers)
        {
            if(s.getClassification() == classification)
            {
                filteredSuppliers.add(s);
            }
        }
        return filteredSuppliers;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getAllOrdersForId(String id)
    {
        logger.debug("getAllOrdersForId({})", id);
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
     * {@inheritDoc}
     */
    @Override
    public Supplier getSupplierById(String id)
    {
        logger.debug("getSupplierById({})", id);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Supplier> getAllSuppliersDate(java.sql.Date from, java.sql.Date to)
    {
        logger.debug("getAllSuppliersDate({}, {})", from, to);
        getDataFromDatabase(false);
        Map<String, Supplier> supplierListDate = new HashMap<>();
        Map<String, Map<String, Order>> supplierMapDate= new HashMap<>();

        Date after = new Date(from.getTime() - 1000 * 60 * 60 * 24);
        Date before = new Date(to.getTime() + 1000 * 60 * 60 * 24);

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
                    if ((order.getDeliveryDate().after(after) && order.getDeliveryDate().before(before))
                            && (order.getPromisedDate().after(after) && order.getPromisedDate().before(before)))
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

        Collections.sort(suppliers, new ClassificationScoreComparator());

        return suppliers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Supplier> getAllSuppliersClassificationDate(Classification classification, Date fromDate, Date toDate)
    {
        List<Supplier> oldSuppliers = getAllSuppliersDate(fromDate, toDate);
        List<Supplier> filteredSuppliers = new ArrayList<>();
        for(Supplier s : oldSuppliers)
        {
            if(s.getClassification() == classification)
            {
                filteredSuppliers.add(s);
            }
        }
        return filteredSuppliers;
    }

    /**
     * {@inheritDoc}
     */
    public State getState()
    {
        return state;
    }
}
