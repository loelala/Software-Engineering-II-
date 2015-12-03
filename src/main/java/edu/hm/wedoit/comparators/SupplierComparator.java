package edu.hm.wedoit.comparators;

import edu.hm.wedoit.model.Supplier;
import edu.hm.wedoit.model.enums.Classification;

import java.util.Comparator;

/**
 * Used to sort a list of suppliers by classification and then by score
 */
public class SupplierComparator implements Comparator<Supplier>
{
    @Override
    public int compare(Supplier s1, Supplier s2)
    {
        Classification c1 = s1.getClassification();
        Classification c2 = s2.getClassification();

        int sComp = Integer.compare(c1.ordinal(), c2.ordinal());

        if (sComp != 0)
        {
            return sComp;
        } else
        {
            sComp = Double.compare(s1.getScore(), s2.getScore()) * (-1);
            if(sComp != 0)
            {
                return sComp;
            }
            else
            {
                return Integer.compare(s1.getNumberOfOrders(), s2.getNumberOfOrders()) * (-1);
            }
        }
    }
}
