package edu.hm.wedoit.settingsmanagement.impl;

import edu.hm.wedoit.dao.AllDao;
import edu.hm.wedoit.model.enums.Classification;
import edu.hm.wedoit.model.limits.ClassificationLimits;
import edu.hm.wedoit.model.limits.ScoringLimits;
import edu.hm.wedoit.settingsmanagement.SettingsManagement;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by B3rni on 03.12.2015.
 */
public class SettingsManagementImpl implements SettingsManagement
{
    private ScoringLimits scoringLimits;
    private ClassificationLimits classificationLimits;
    private final String rootDir;
    private final File scoreFile;
    private final File classificationFile;
    private final static String SCORING_FILE = "scoresettings.dat";
    private final static String CLASSIFICATION_FILE = "classificationsettings.dat";

    @Autowired
    private AllDao dao;

    private SettingsManagementImpl(String rootDir)
    {
        this.rootDir = rootDir;
        scoreFile = new File(this.rootDir + File.separator+ SCORING_FILE);
        classificationFile = new File(this.rootDir + File.separator+ CLASSIFICATION_FILE);
        scoringLimits = loadScoringLimits();
        classificationLimits = loadClassificationLimits();
    }

    private ClassificationLimits loadClassificationLimits()
    {
        ClassificationLimits classificationLimits;

        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(classificationFile))))
        {
            classificationLimits = (ClassificationLimits)ois.readObject();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            classificationLimits = loadDefaultClassificationSettings();
        }
        return classificationLimits;
    }

    private ClassificationLimits loadDefaultClassificationSettings()
    {

        Map<String, Integer> map = new HashMap<>();
        map.put(Classification.TOP.toString(), 20);
        map.put(Classification.NORMAL.toString(), 3);
        map.put(Classification.ONE_OFF.toString(), 1);
        return new ClassificationLimits(map);
    }

    private ScoringLimits loadScoringLimits()
    {

        ScoringLimits scoringLimits;

        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(scoreFile))))
        {
            scoringLimits = (ScoringLimits)ois.readObject();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            scoringLimits = loadDefaultScoreSettings();
        }
        return scoringLimits;
    }

    private ScoringLimits loadDefaultScoreSettings()
    {
        Map<String, Integer> map = new HashMap<>();
        map.put(ScoringLimits.Limits.PMIN100.toString(), 0);
        map.put(ScoringLimits.Limits.PMAX100.toString(), 0);
        map.put(ScoringLimits.Limits.NMIN100.toString(), -1);
        map.put(ScoringLimits.Limits.NMAX100.toString(), -1);

        map.put(ScoringLimits.Limits.PMAX90.toString(), 3);
        map.put(ScoringLimits.Limits.NMIN90.toString(), -2);

        map.put(ScoringLimits.Limits.PMAX80.toString(), 7);
        map.put(ScoringLimits.Limits.NMIN80.toString(), -3);

        map.put(ScoringLimits.Limits.PMAX60.toString(), 14);
        map.put(ScoringLimits.Limits.NMIN60.toString(), -7);

        map.put(ScoringLimits.Limits.PMAX40.toString(), 28);
        map.put(ScoringLimits.Limits.NMIN40.toString(), -10);

        return new ScoringLimits(map);
    }

    @Override
    public ScoringLimits getScoringLimits()
    {
        return scoringLimits;
    }

    @Override
    public ClassificationLimits getClassificationLimits()
    {
        return classificationLimits;
    }

    @Override
    public void setScoringlimits(ScoringLimits sl)
    {
        scoringLimits = sl;
        saveScoreLimits();
        dao.renewCache();
    }

    private void saveScoreLimits()
    {

        try(ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream (new FileOutputStream(scoreFile))))
        {
            if(scoringLimits != null )
            {
                oos.writeObject(scoringLimits);
            }
            else
            {
                throw new IllegalArgumentException();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();

        }
    }

    @Override
    public void setClassificationLimits(ClassificationLimits cl)
    {
        classificationLimits = cl;
        saveClassificationLimits();
        dao.renewCache();
    }

    private void saveClassificationLimits()
    {
        try(ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream (new FileOutputStream(classificationFile))))
        {
            if(classificationLimits != null )
            {
                oos.writeObject(classificationLimits);
            }
            else
            {
                throw new IllegalArgumentException();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();

        }
    }
}
