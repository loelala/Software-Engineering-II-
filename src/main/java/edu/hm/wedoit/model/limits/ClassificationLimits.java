package edu.hm.wedoit.model.limits;

import edu.hm.wedoit.model.enums.Classification;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joncn on 03.12.2015.
 */
public class ClassificationLimits implements Serializable
{

    public ClassificationLimits(){}
    private Map<String, Integer> classificationLimits;

    public ClassificationLimits(Map<String, Integer> classificationLimits)
    {
        this.classificationLimits = classificationLimits;
    }

    public Map<String, Integer> getClassificationLimits()
    {
        return classificationLimits;
    }

    public int getClassificationLimit(Classification c)
    {
        return classificationLimits.get(c.toString());
    }

    public void setClassificationLimits(Map<String, Integer> limits)
    {
        classificationLimits = limits;
    }

}
