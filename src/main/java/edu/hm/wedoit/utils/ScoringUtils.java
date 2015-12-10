package edu.hm.wedoit.utils;

import edu.hm.wedoit.model.limits.ScoringLimits;

import java.util.Map;

/**
 * Created by Joncn on 10.12.2015.
 */
public class ScoringUtils
{
    private static volatile ScoringUtils instance;

    private ScoringUtils()
    {

    }

    public static ScoringUtils getInstance()
    {
        if(instance == null)
        {
            synchronized (ScoringUtils.class)
            {
                if(instance == null)
                {
                    instance = new ScoringUtils();
                }
            }
        }
        return instance;
    }

    public boolean validateScoringLimits(ScoringLimits sl)
    {
        if(sl == null)
        {
            return false;
        }

        int pMin100 = sl.getScoreLimit(ScoringLimits.Limits.PMIN100);
        int pMax100 = sl.getScoreLimit(ScoringLimits.Limits.PMAX100);
        int nMin100 = sl.getScoreLimit(ScoringLimits.Limits.NMIN100);
        int nMax100 = sl.getScoreLimit(ScoringLimits.Limits.NMAX100);

        int pMin90 = pMax100 +1;
        int pMax90 = sl.getScoreLimit(ScoringLimits.Limits.PMAX90);
        int nMin90 = sl.getScoreLimit(ScoringLimits.Limits.NMIN90);
        int nMax90 = nMin100 -1;

        int pMin80 = pMax90 +1;
        int pMax80 = sl.getScoreLimit(ScoringLimits.Limits.PMAX80);
        int nMin80 = sl.getScoreLimit(ScoringLimits.Limits.NMIN80);
        int nMax80 = nMin80 -1;

        int pMin60 = pMax80 + 1;
        int pMax60 = sl.getScoreLimit(ScoringLimits.Limits.PMAX60);
        int nMin60 = sl.getScoreLimit(ScoringLimits.Limits.NMIN60);
        int nMax60 = nMin80 -1;

        int pMin40 = pMax60 +1;
        int pMax40 = sl.getScoreLimit(ScoringLimits.Limits.PMAX40);
        int nMin40 = sl.getScoreLimit(ScoringLimits.Limits.NMIN40);
        int nMax40 = nMin60 -1;

        return true;
//        return(pMin100 <= pMax100 && nMin100 <= nMax100
//                && pMin90 <= pMax90 && nMin90 <= nMax90
//                && pMin80 <= pMax80 && nMin80 <= nMax80
//                && pMin60 <= pMax60 && nMin60 <= nMax60
//                && pMin40 <= pMax40 && nMin40 <= nMax40);
    }
}
