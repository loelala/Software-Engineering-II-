package edu.hm.wedoit.rest;

import edu.hm.wedoit.model.limits.ClassificationLimits;
import edu.hm.wedoit.model.limits.ScoringLimits;
import edu.hm.wedoit.settingsmanagement.SettingsManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Joncn on 10.12.2015.
 */
@RestController
@RequestMapping("/api/settings")
public class SettingsController
{
    private final static Logger logger = LoggerFactory.getLogger(SupplierController.class);

    @Autowired
    SettingsManagement sm;

    @RequestMapping("/classification")
    public ClassificationLimits getClassificationLimits()
    {
        logger.debug("getClassificationLimits()");
        return sm.getClassificationLimits();
    }

    @RequestMapping("/scoring")
    public ScoringLimits getScoringLimits()
    {
        logger.debug("getScoringLimits()");
        return sm.getScoringLimits();
    }
}
