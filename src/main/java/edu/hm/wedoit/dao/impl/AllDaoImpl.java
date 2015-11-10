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



                /*
                System.out.println("No suppliers fetched yet!");
                Map<String, List<Order>> allMap = new HashMap<>();

                final String supplierQuery = "SELECT NAME1, LIFNR FROM lfa1;";

                System.out.println("Querying all suppliers");

                time = System.currentTimeMillis();
                List<Supplier> supplierList = jdbcTemplate.query(supplierQuery, new SupplierWithScoreMapper());

                System.out.println("Querying all suppliers: " + (System.currentTimeMillis() - time) + " ms");
                System.out.println("Creating new suppliers");

                for (Supplier s : supplierList) {
                    allMap.put(s.getId(), new ArrayList<Order>());
                }

                System.out.println("Creating new suppliers: " + (System.currentTimeMillis() - time) + " ms");

                System.out.println("Start fetching all data");
                time = System.currentTimeMillis();

                final String dataQuery =
                        "SELECT tmp5.LIFNR, NAME1, tmp6.EBELN, BLDAT, SLFDT FROM\n" +
                        "\n" +
                        "\t(SELECT tmp3.LIFNR, NAME1, tmp4.EBELN, BLDAT FROM\n" +
                        "\n" +
                        "\t\t(SELECT tmp1.LIFNR, NAME1, EBELN FROM\n" +
                        "\t\t\t(SELECT LIFNR, NAME1 FROM sap_emulation.lfa1) as tmp1\n" +
                        "\t\t\tINNER JOIN\n" +
                        "\t\t\t(SELECT LIFNR, EBELN FROM sap_emulation.ekko) as tmp2\n" +
                        "\t\t\tON tmp2.LIFNR = tmp1.LIFNR) as tmp3\n" +
                        "\t\t\t\n" +
                        "\t\tINNER JOIN    \n" +
                        "\t\t\n" +
                        "\t\t(SELECT EBELN, BLDAT FROM sap_emulation.ekbe WHERE BEWTP = 'E' AND BWART = 101) as tmp4\n" +
                        "\t\tON tmp3.EBELN = tmp4.EBELN\n" +
                        "        ) as tmp5\n" +
                        "        \n" +
                        "\tINNER JOIN\n" +
                        "        \n" +
                        "\t(SELECT EBELN, SLFDT FROM sap_emulation.eket) as tmp6\n" +
                        "    ON tmp5.EBELN = tmp6.EBELN";

                //jdbcTemplate.query(dataQuery, new OrderMapper(allMap));
                jdbcTemplate.query(dataQuery, new OrderRowCallbackHandler(allMap));

                System.out.println("Start fetching all data: " + (System.currentTimeMillis() - time) + " ms");

                System.out.println("Calculating score");


                all = supplierList;
                for (Supplier s : all)
                {
                    s.setOrders(allMap.get(s.getId()));
                }

                System.out.println("Calculating: " + (System.currentTimeMillis() - time) + " ms");
                Collections.sort(all, new Comparator<Supplier>() {
                    @Override
                    public int compare(Supplier o1, Supplier o2) {
                        return Double.compare(o1.getScore(), o2.getScore());
                    }
                });*/
}
