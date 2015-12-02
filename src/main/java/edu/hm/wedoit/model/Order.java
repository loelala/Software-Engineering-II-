package edu.hm.wedoit.model;

import java.util.Date;

/**
 * One order for a supplier
 */
public class Order
{
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
        calculateScore();
        calculateDifference();
    }

    private void calculateDifference()
    {
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
    }

    private void calculateScore()
    {
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
    }

    private boolean checkScoreRange(int dayCount, int pMin, int pMax, int nMin, int nMax)
    {
        return (dayCount >= pMin && dayCount <= pMax) || (dayCount >= nMin && dayCount <= nMax);
    }
}
