package edu.hm.wedoit.dao.impl;

import edu.hm.wedoit.dao.OrderDao;
import edu.hm.wedoit.mapper.OrderMapper;
import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.SupplierWithScore;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Joncn on 02.11.2015.
 */
@Repository
public class OrderDaoImpl extends AbstractDao implements OrderDao
{

    @Override
    public List<Order> getAllOrdersForId(String id) {
        System.out.println("getAllOrdersForId: " + id);
        final String query =

        "SELECT LIFNR, NAME1, liefUSlfdt.EBELN, SLFDT, BUDAT " +
        "FROM " +
                "(SELECT LIFNR, NAME1, liefUOrder.EBELN, SLFDT " +
                        "FROM " +
                                "(SELECT lieferant.LIFNR, lieferant.NAME1, ekko.EBELN " +
                                        "FROM (	SELECT LIFNR, NAME1 " +
                                                "FROM sap_emulation.lfa1 " +
                                                "WHERE LIFNR = " + id + ") as lieferant " +
                                        "INNER JOIN sap_emulation.ekko " +
                                        "ON lieferant.LIFNR=ekko.LIFNR " +
                                        "GROUP BY EBELN) as liefUOrder " +

                        "INNER JOIN sap_emulation.eket " +
                        "ON liefUOrder.EBELN = eket.EBELN) as liefUSlfdt " +
        "INNER JOIN sap_emulation.ekbe " +
        "ON liefUSlfdt.EBELN = ekbe.EBELN;";

        List<Order> orders  = jdbcTemplate.query(query,new OrderMapper());
        return orders;
    }
}
