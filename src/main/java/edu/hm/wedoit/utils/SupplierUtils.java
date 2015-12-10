package edu.hm.wedoit.utils;

import edu.hm.wedoit.model.enums.Classification;
import edu.hm.wedoit.model.enums.DeliveryDifference;
import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.limits.ClassificationLimits;
import edu.hm.wedoit.settingsmanagement.SettingsManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Joncn on 03.12.2015.
 */
public class SupplierUtils
{
    @Autowired
    SettingsManagement sm;

    private final static Logger logger = LoggerFactory.getLogger(SupplierUtils.class);

    private static volatile SupplierUtils instance;

    private SupplierUtils()
    {

    }

    public static SupplierUtils getInstance()
    {
        if(instance == null)
        {
            synchronized (SupplierUtils.class)
            {
                if(instance == null)
                {
                    instance = new SupplierUtils();
                }
            }
        }
        return instance;
    }

    public int[] calculateDifferences(List<Order> orders)
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

    public double calculateScore(List<Order> orders)
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

    public Classification calculateClassification(int numberOfOrders)
    {
        //logger.debug("calculateClassification({})", numberOfOrders);

        ClassificationLimits cl = sm.getClassificationLimits();
        Classification c;

        if(numberOfOrders <= 0)
        {
            c = Classification.NONE;
        }
        else if(numberOfOrders > 0 && numberOfOrders <= cl.getClassificationLimit(Classification.ONE_OFF))
        {
            c = Classification.ONE_OFF;
        }
        else if(numberOfOrders > cl.getClassificationLimit(Classification.ONE_OFF) && numberOfOrders <= cl.getClassificationLimit(Classification.NORMAL))
        {
            c = Classification.NORMAL;
        }
        else if(numberOfOrders >= cl.getClassificationLimit(Classification.TOP))
        {
            c = Classification.TOP;
        }
        else
        {
            c = Classification.NONE;
        }
        logger.debug("calculateClassification({}) returns {}", numberOfOrders, c);
        return c;
    }
}
