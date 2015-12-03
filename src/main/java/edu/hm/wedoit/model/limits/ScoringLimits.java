package edu.hm.wedoit.model.limits;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joncn on 03.12.2015.
 */
public class ScoringLimits
{
    public enum limits
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

    public static Map<String, Integer> getDefaultMap()
    {
        Map<String, Integer> map = new HashMap<>();
        map.put(limits.PMIN100.toString(), 0);
        map.put(limits.PMAX100.toString(), 0);
        map.put(limits.NMIN100.toString(), -1);
        map.put(limits.NMAX100.toString(), -1);

        map.put(limits.PMAX90.toString(), 3);
        map.put(limits.NMIN90.toString(), -2);

        map.put(limits.PMAX80.toString(), 7);
        map.put(limits.NMIN80.toString(), -3);

        map.put(limits.PMAX60.toString(), 14);
        map.put(limits.NMIN60.toString(), -7);

        map.put(limits.PMAX40.toString(), 28);
        map.put(limits.NMIN40.toString(), -10);

        return map;
    }
}
