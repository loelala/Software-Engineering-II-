package edu.hm.wedoit.model;


import java.util.List;

/**
 * Created by Joncn on 31.10.2015.
 */
public class SupplierWithScore extends Supplier {

    private List<Order> orders;
    private float score = -1;

    public float getScore()
    {
        if(score == -1)
        {
            calculateScore();
        }
        return score;
    }

    public void setScore(float score)
    {
        this.score = score;
    }

    public List<Order> getOrders() { return orders; }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
        calculateScore();
    }

    private void calculateScore()
    {
        int tmpScore = 0;
        for(Order o : orders)
        {
            tmpScore += o.getOrderScore();
        }
        this.score = ((float)tmpScore) / orders.size();
        return;
    }
}
