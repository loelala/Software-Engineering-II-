package edu.hm.wedoit.model;


import java.util.List;

/**
 * Created by Joncn on 31.10.2015.
 */
public class Supplier
{
    private String id;
    private String name;
    private List<Order> orders;
    private int numberOfOrders;
    private double score = -1;

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
            calculateScore();
        }
        return score;
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
        this.numberOfOrders = this.orders.size();
    }

    public void setScore(double score)
    {
        this.score = score;
    }

    private void calculateScore()
    {
        if(orders != null && orders.size() != 0)
        {
            int tmpScore = 0;
            for (Order o : orders) {
                tmpScore += o.getOrderScore();
            }
            this.score = Math.round((((double) tmpScore) / orders.size()) * 100) / 100;
        }
        else
        {
            this.score = 0;
        }
        return;
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
