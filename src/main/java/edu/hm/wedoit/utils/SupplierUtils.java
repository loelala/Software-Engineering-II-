package edu.hm.wedoit.utils;

import edu.hm.wedoit.model.Classification;
import edu.hm.wedoit.model.DeliveryDifference;
import edu.hm.wedoit.model.Order;

import java.util.List;

/**
 * Created by Joncn on 03.12.2015.
 */
public class SupplierUtils
{
    public static int[] calculateDifferences(List<Order> orders)
    {
        int[] deliveryDifferences = new int[DeliveryDifference.COUNT];
        for(Order o : orders)
        {
            switch(o.getDeliveryDifference())
            {
                case MUCH_TOO_EARLY:
                    deliveryDifferences[DeliveryDifference.MUCH_TOO_EARLY.ordinal()]++;
                    break;
                case TOO_EARLY:
                    deliveryDifferences[DeliveryDifference.TOO_EARLY.ordinal()]++;
                    break;
                case ON_TIME:
                    deliveryDifferences[DeliveryDifference.ON_TIME.ordinal()]++;
                    break;
                case TOO_LATE:
                    deliveryDifferences[DeliveryDifference.TOO_LATE.ordinal()]++;
                    break;
                case MUCH_TOO_LATE:
                    deliveryDifferences[DeliveryDifference.MUCH_TOO_LATE.ordinal()]++;
                    break;
                case NOT_CALCULATED:
                    deliveryDifferences[DeliveryDifference.NOT_CALCULATED.ordinal()]++;
                    break;
            }
        }
        return deliveryDifferences;
    }

    public static double calculateScore(List<Order> orders)
    {
        double score;
        if(orders != null && orders.size() != 0)
        {
            int tmpScore = 0;
            for (Order o : orders) {
                tmpScore += o.getOrderScore();
            }
            score = Math.round((((double) tmpScore) / orders.size()) * 100) / 100;
        }
        else
        {
            score = 0;
        }
        return score;
    }

    public static Classification calculateClassification(int numberOfOrders)
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
}
