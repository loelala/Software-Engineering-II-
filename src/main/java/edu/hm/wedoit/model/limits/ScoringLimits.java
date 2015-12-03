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

    public ScoringLimits(Map<String, Integer> scoringLimits)
    {
        this.scoringLimits = scoringLimits;
    }

    public Map<String, Integer> getScoringLimits()
    {
        return scoringLimits;
    }

    public int getScoreLimit(Limits l)
    {
        return scoringLimits.get(l.toString());
    }

    public void setScoringLimits(Map<String, Integer> limits)
    {
        scoringLimits = limits;
    }
}
