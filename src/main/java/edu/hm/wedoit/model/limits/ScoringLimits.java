package edu.hm.wedoit.model.limits;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joncn on 03.12.2015.
 */
public class ScoringLimits
{
    public enum Limits
    {
        PMIN100,
        PMAX100,
        NMIN100,
        NMAX100,

        PMAX90,
        NMIN90,

        PMAX80,
        NMIN80,

        PMAX60,
        NMIN60,

        PMAX40,
        NMIN40,
    }

    private Map<String, Integer> scoringLimits;

    public Map<String, Integer> getScoringLimits()
    {
        if(scoringLimits == null)
        {
            return getDefaultMap();
        }
        return scoringLimits;
    }

    public void setScoringLimits(Map<String, Integer> limits)
    {
        scoringLimits = limits;
    }

    public Map<String, Integer> getDefaultMap()
    {
        Map<String, Integer> map = new HashMap<>();
        map.put(Limits.PMIN100.toString(), 0);
        map.put(Limits.PMAX100.toString(), 0);
        map.put(Limits.NMIN100.toString(), -1);
        map.put(Limits.NMAX100.toString(), -1);

        map.put(Limits.PMAX90.toString(), 3);
        map.put(Limits.NMIN90.toString(), -2);

        map.put(Limits.PMAX80.toString(), 7);
        map.put(Limits.NMIN80.toString(), -3);

        map.put(Limits.PMAX60.toString(), 14);
        map.put(Limits.NMIN60.toString(), -7);

        map.put(Limits.PMAX40.toString(), 28);
        map.put(Limits.NMIN40.toString(), -10);

        return map;
    }
}
