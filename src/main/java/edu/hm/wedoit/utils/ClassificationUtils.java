package edu.hm.wedoit.utils;

import edu.hm.wedoit.model.enums.Classification;
import edu.hm.wedoit.model.limits.ClassificationLimits;

/**
 * Created by Joncn on 10.12.2015.
 */
public class ClassificationUtils
{
    private static volatile ClassificationUtils instance;

    private ClassificationUtils()
    {

    }

    public static ClassificationUtils getInstance()
    {
        if(instance == null)
        {
            synchronized (ClassificationUtils.class)
            {
                if(instance == null)
                {
                    instance = new ClassificationUtils();
                }
            }
        }
        return instance;
    }

    public boolean validateClassificationLimits(ClassificationLimits cl)
    {
        if(cl == null)
        {
            return false;
        }

        int top = cl.getClassificationLimit(Classification.TOP);
        int normal = cl.getClassificationLimit(Classification.NORMAL);
        int oneoff = cl.getClassificationLimit(Classification.ONE_OFF);

        if(top > normal && normal > oneoff)
        {
            return true;
        }
        return false;
    }
}
