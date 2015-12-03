package edu.hm.wedoit.utils;

import edu.hm.wedoit.model.DeliveryDifference;

import java.util.Date;

/**
 * Created by Joncn on 03.12.2015.
 */
public class OrderUtils
{
    public static DeliveryDifference calculateDifference(Date deliveryDate, Date promisedDate)
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

    public static int calculateScore(Date deliveryDate, Date promisedDate)
    {
        int orderScore;
        if(deliveryDate != null && promisedDate != null)
        {
            long diff = deliveryDate.getTime() - promisedDate.getTime();

            int dayCount = (int) diff / (24 * 60 * 60 * 1000);

            // 0 or -1
            int pMin100 = 0;
            int pMax100 = 0;
            int nMin100 = -1;
            int nMax100 = -1;

            // 1 - 3 or -2
            int pMin90 = pMax100 +1;
            int pMax90 = 3;
            int nMin90 = -2;
            int nMax90 = nMin100 -1;

            // 4 - 7 or -3
            int pMin80 = pMax90 +1;
            int pMax80 = 7;
            int nMin80 = -3;
            int nMax80 = nMin80 -1;

            // 8 - 14 or -4 - -7
            int pMin60 = pMax80 + 1;
            int pMax60 = 14;
            int nMin60 = -7;
            int nMax60 = nMin80 -1;

            // 15 - 28 or -8 - -10
            int pMin40 = pMax60 +1;
            int pMax40 = 28;
            int nMin40 = -10;
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

    private static boolean checkScoreRange(int dayCount, int pMin, int pMax, int nMin, int nMax)
    {
        return (dayCount >= pMin && dayCount <= pMax) || (dayCount >= nMin && dayCount <= nMax);
    }
}
