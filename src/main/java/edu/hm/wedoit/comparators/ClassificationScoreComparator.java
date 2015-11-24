package edu.hm.wedoit.comparators;

import edu.hm.wedoit.model.Supplier;

import java.util.Comparator;

/**
 * Created by Joncn on 24.11.2015.
 */
public class ClassificationScoreComparator implements Comparator<Supplier>
{
    @Override
    public int compare(Supplier o1, Supplier o2)
    {
        String c1 = ((Supplier) o1).getClassification();
        String c2 = ((Supplier) o2).getClassification();

        int sComp = c1.compareTo(c2) *(-1);

        if (sComp != 0)
        {
            return sComp;
        } else
        {
            return Double.compare(o1.getScore(), o2.getScore()) * (-1);
        }
    }
}
