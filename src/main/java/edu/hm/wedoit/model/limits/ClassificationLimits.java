package edu.hm.wedoit.model.limits;

import edu.hm.wedoit.model.enums.Classification;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joncn on 03.12.2015.
 */
public class ClassificationLimits
{
    private Map<String, Integer> classificationLimits;

    public Map<String, Integer> getClassificationLimits()
    {
        if(classificationLimits == null)
        {
            return getDefaultClassificationLimits();
        }
        return classificationLimits;
    }

    public void setClassificationLimits(Map<String, Integer> limits)
    {
        classificationLimits = limits;
    }

    private Map<String, Integer> getDefaultClassificationLimits()
    {
        Map<String, Integer> map = new HashMap<>();
        map.put(Classification.TOP.toString(), 20);
        map.put(Classification.NORMAL.toString(), 3);
        map.put(Classification.ONE_OFF.toString(), 1);
        return map;
    }
}
