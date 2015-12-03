package edu.hm.wedoit.utils;

import edu.hm.wedoit.model.enums.DeliveryDifference;
import edu.hm.wedoit.model.limits.ScoringLimits;
import edu.hm.wedoit.settingsmanagement.SettingsManagement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Joncn on 03.12.2015.
 */
public class OrderUtils
{
    @Autowired
    private SettingsManagement sm;

    private static volatile OrderUtils instance;

    private OrderUtils()
    {

    }

    public static OrderUtils getInstance()
    {
        if(instance == null)
        {
            synchronized (OrderUtils.class)
            {
                if(instance == null)
                {
                    instance = new OrderUtils();
                }
            }
        }
        return instance;
    }

    public DeliveryDifference calculateDifference(Date deliveryDate, Date promisedDate)
    {
        DeliveryDifference deliveryDifference;
        if(deliveryDate != null && promisedDate != null)
        {
            long diff = deliveryDate.getTime() - promisedDate.getTime();
            int dayCount = (int) diff / (24 * 60 * 60 * 1000);

            // -1 <= x = 0
            int minOnTime = -1;
            int maxOnTime = 0;

            // 0 < x <= 5
            int minLate = maxOnTime +1;
            int maxLate = 5;

            // -5 <= x < -1
            int minEarly = -5;
            int maxEarly = minOnTime -1;


            if(dayCount >= minOnTime && dayCount <= maxOnTime)
            {
                deliveryDifference = DeliveryDifference.ON_TIME;
            }
            else if(dayCount >= minLate && dayCount <= maxLate)
            {
                deliveryDifference = DeliveryDifference.TOO_LATE;
            }
            else if(dayCount >= minEarly && dayCount <= maxEarly)
            {
                deliveryDifference = DeliveryDifference.TOO_EARLY;
            }
            else if(dayCount > maxLate)
            {
                deliveryDifference = DeliveryDifference.MUCH_TOO_LATE;
            }
            else if(dayCount < minEarly)
            {
                deliveryDifference = DeliveryDifference.MUCH_TOO_EARLY;
            }
            else
            {
                deliveryDifference = DeliveryDifference.NOT_CALCULATED;
            }
        }
        else
        {
            deliveryDifference = DeliveryDifference.NOT_CALCULATED;
        }
        return deliveryDifference;
    }

    public int calculateScore(Date deliveryDate, Date promisedDate)
    {
        int orderScore;
        if(deliveryDate != null && promisedDate != null)
        {
            long diff = deliveryDate.getTime() - promisedDate.getTime();

            int dayCount = (int) diff / (24 * 60 * 60 * 1000);

            ScoringLimits sl = sm.getScoringLimits();

            int pMin100 = sl.getScoreLimit(ScoringLimits.Limits.PMIN100);
            int pMax100 = sl.getScoreLimit(ScoringLimits.Limits.PMAX100);
            int nMin100 = sl.getScoreLimit(ScoringLimits.Limits.NMIN100);
            int nMax100 = sl.getScoreLimit(ScoringLimits.Limits.NMAX100);

            int pMin90 = pMax100 +1;
            int pMax90 = sl.getScoreLimit(ScoringLimits.Limits.PMAX90);
            int nMin90 = sl.getScoreLimit(ScoringLimits.Limits.NMIN90);
            int nMax90 = nMin100 -1;

            int pMin80 = pMax90 +1;
            int pMax80 = sl.getScoreLimit(ScoringLimits.Limits.PMAX80);
            int nMin80 = sl.getScoreLimit(ScoringLimits.Limits.NMIN80);
            int nMax80 = nMin80 -1;

            int pMin60 = pMax80 + 1;
            int pMax60 = sl.getScoreLimit(ScoringLimits.Limits.PMAX60);
            int nMin60 = sl.getScoreLimit(ScoringLimits.Limits.NMIN60);
            int nMax60 = nMin80 -1;

            int pMin40 = pMax60 +1;
            int pMax40 = sl.getScoreLimit(ScoringLimits.Limits.PMAX40);
            int nMin40 = sl.getScoreLimit(ScoringLimits.Limits.NMIN40);
            int nMax40 = nMin60 -1;

            if(checkScoreRange(dayCount, pMin100, pMax100, nMin100, nMax100))
            {
                orderScore = 100;
            }
            else if(checkScoreRange(dayCount, pMin90, pMax90, nMin90, nMax90))
            {
                orderScore = 90;
            }
            else if(checkScoreRange(dayCount, pMin80, pMax80, nMin80, nMax80))
            {
                orderScore = 80;
            }
            else if(checkScoreRange(dayCount, pMin60, pMax60, nMin60, nMax60))
            {
                orderScore = 60;
            }
            else if(checkScoreRange(dayCount, pMin40, pMax40, nMin40, nMax40))
            {
                orderScore = 40;
            }
            else
            {
                orderScore = 0;
            }
        }
        else
        {
            orderScore = 0;
        }
        return orderScore;
    }

    private boolean checkScoreRange(int dayCount, int pMin, int pMax, int nMin, int nMax)
    {
        return (dayCount >= pMin && dayCount <= pMax) || (dayCount >= nMin && dayCount <= nMax);
    }
}
