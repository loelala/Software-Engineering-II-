package edu.hm.wedoit.model;

import java.util.Date;

/**
 * Created by Joncn on 02.11.2015.
 */
public class Order {
    private Date promisedDate;
    private Date deliveryDate;
    private String ebeln;

    private int orderScore = -1;

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
            calculateScore();
        }
        return orderScore;
    }

    public void setPromisedDate(Date promisedDate)
    {
        this.promisedDate = promisedDate;
        if(deliveryDate != null)
        {
            calculateScore();
        }
    }

    public void setDeliveryDate(Date deliveryDate)
    {
        this.deliveryDate = deliveryDate;
        if(promisedDate != null)
        {
            calculateScore();
        }
    }

    public void setEbeln(String ebeln)
    {
        this.ebeln = ebeln;
    }

    private void calculateScore()
    {
        if(deliveryDate != null && promisedDate != null)
        {
            long diff = deliveryDate.getTime() - promisedDate.getTime();

            int dayCount = (int) diff / (24 * 60 * 60 * 1000);

            switch (dayCount) {
                //0 or -1
                case 0:
                case -1:
                    orderScore = 100;
                    break;

                //1 - 3 or -2
                case 1:
                case 2:
                case 3:
                case -2:
                    orderScore = 90;
                    break;

                //4 - 7 or -3
                case 4:
                case 5:
                case 6:
                case 7:
                case -3:
                    orderScore = 80;
                    break;

                //8 - 14 or -4 - -7
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case -4:
                case -5:
                case -6:
                case -7:
                    orderScore = 60;
                    break;

                //15 - 28 or -8 - -10
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case -8:
                case -9:
                case -10:
                    orderScore = 40;
                    break;

                //29 - ~ or -11 - ~
                default:
                    orderScore = 0;
                    break;
            }
        }
        else
        {
            orderScore = 0;
        }

        return;
    }
}
