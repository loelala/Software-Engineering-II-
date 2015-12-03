package edu.hm.wedoit.settingsmanagement;

import edu.hm.wedoit.model.limits.ClassificationLimits;
import edu.hm.wedoit.model.limits.ScoringLimits;

/**
 * Created by B3rni on 03.12.2015.
 */
public interface SettingsManagement
{
    public ScoringLimits getScoringLimits();
    public ClassificationLimits getClassificationLimits();

    public void setScoringlimits(ScoringLimits sl);
    public void setClassificationLimits(ClassificationLimits cl);
}
