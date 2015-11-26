package edu.hm.wedoit.model;


import java.util.List;

/**
 * One Supplier with score, orders and classification
 */
public class Supplier
{
    private String id;
    private String name;
    private List<Order> orders;
    private int numberOfOrders;
    private double score = -1;
    private Classification classification;

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

    public Classification getClassification()
    {
        return classification;
    }

    public String getClassificationAsString()
    {
        return classification.toString();
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
        this.classification = calculateClassification();
    }

    public void setScore(double score)
    {
        this.score = score;
    }

    public void setClassification(Classification classification)
    {
        this.classification = classification;
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

    private Classification calculateClassification()
    {
        if(numberOfOrders >= 20)
        {
            return Classification.TOP;
        }
        else if(numberOfOrders < 20 && numberOfOrders > 2)
        {
            return Classification.NORMAL;
        }
        else if(numberOfOrders == 1 || numberOfOrders == 2)
        {
            return Classification.ONE_OFF;
        }
        else
        {
            return Classification.NONE;
        }
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
