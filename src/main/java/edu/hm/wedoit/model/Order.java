package edu.hm.wedoit.model;

import edu.hm.wedoit.model.enums.DeliveryDifference;
import edu.hm.wedoit.utils.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * One order for a supplier
 */
public class Order
{
    private OrderUtils ou = OrderUtils.getInstance();

    private Date promisedDate;
    private Date deliveryDate;
    private String ebeln;
    private int orderScore = -1;
    private DeliveryDifference deliveryDifference;

    public Order(String ebeln, Date promisedDate, Date deliveryDate)
    {
        this.ebeln = ebeln;
        this.promisedDate = promisedDate;
        this.deliveryDate = deliveryDate;
    }

    public Order(String ebeln)
    {
        this.ebeln = ebeln;
    }

    public Order(Order order)
    {
        this.ebeln = order.getEbeln();
        setDeliveryDate(order.getDeliveryDate());
        setPromisedDate(order.getPromisedDate());
    }

    public Order(){};

    public Date getPromisedDate()
    {
        return promisedDate;
    }

    public Date getDeliveryDate()
    {
        return deliveryDate;
    }

    public String getEbeln()
    {
        return ebeln;
    }

    public int getOrderScore()
    {
        if(orderScore == -1)
        {
            calculateScoreAndDeliveryDifference();
        }
        return orderScore;
    }

    public DeliveryDifference getDeliveryDifference()
    {
        if(deliveryDifference == null)
        {
            calculateScoreAndDeliveryDifference();
        }
        return deliveryDifference;
    }

    public void setPromisedDate(Date promisedDate)
    {
        this.promisedDate = promisedDate;
        if(deliveryDate != null)
        {
            calculateScoreAndDeliveryDifference();
        }
    }

    public void setDeliveryDate(Date deliveryDate)
    {
        this.deliveryDate = deliveryDate;
        if(promisedDate != null)
        {
            calculateScoreAndDeliveryDifference();
        }
    }

    public void setDeliveryDifference(DeliveryDifference deliveryDifference)
    {
        this.deliveryDifference = deliveryDifference;
    }

    public void setEbeln(String ebeln)
    {
        this.ebeln = ebeln;
    }

    private void calculateScoreAndDeliveryDifference()
    {
        orderScore = ou.calculateScore(deliveryDate, promisedDate);
        deliveryDifference = ou.calculateDifference(deliveryDate, promisedDate);
    }
}
