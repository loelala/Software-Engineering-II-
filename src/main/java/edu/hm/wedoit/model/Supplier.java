package edu.hm.wedoit.model;


import edu.hm.wedoit.model.enums.Classification;
import edu.hm.wedoit.utils.SupplierUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * One Supplier with score, orders and classification
 */
public class Supplier
{
    private SupplierUtils su = SupplierUtils.getInstance();

    private String id;
    private String name;
    private List<Order> orders;
    private int numberOfOrders;
    private double score = -1;
    private Classification classification;
    private int[] deliveryDifferences;

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public List<Order> getOrders()
    {
        return orders;
    }

    public int getNumberOfOrders()
    {
        return this.numberOfOrders;
    }

    public double getScore()
    {
        if(score == -1)
        {
            score = su.calculateScore(orders);
        }
        return score;
    }

    public int[] getDeliveryDifferences()
    {
        return deliveryDifferences;
    }

    public Classification getClassification()
    {
        return classification;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
        numberOfOrders = this.orders.size();
        classification = su.calculateClassification(numberOfOrders);
        deliveryDifferences = su.calculateDifferences(orders);
    }

    public void setScore(double score)
    {
        this.score = score;
    }

    public void setClassification(Classification classification)
    {
        this.classification = classification;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null)
        {
            return false;
        }
        if(o == this) {
            return true;
        }
        if( !(o instanceof Supplier))
        {
            return false;
        }
        Supplier s = (Supplier) o;
        return s.getId().equals(this.getId());
    }
}
