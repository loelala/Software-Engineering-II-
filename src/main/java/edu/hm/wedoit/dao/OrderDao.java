package edu.hm.wedoit.dao;

import edu.hm.wedoit.model.Order;

import java.util.List;

/**
 * Created by Joncn on 02.11.2015.
 */
public interface OrderDao
{
    public List<Order> getAllOrdersForId(String id);
}
