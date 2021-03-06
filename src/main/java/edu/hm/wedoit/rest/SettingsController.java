package edu.hm.wedoit.rest;

import edu.hm.wedoit.dao.AllDao;
import edu.hm.wedoit.model.limits.ClassificationLimits;
import edu.hm.wedoit.model.limits.ScoringLimits;
import edu.hm.wedoit.settingsmanagement.SettingsManagement;
import edu.hm.wedoit.utils.ClassificationUtils;
import edu.hm.wedoit.utils.ScoringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Joncn on 10.12.2015.
 */
@RestController
@RequestMapping("/api/settings")
public class SettingsController
{
    private final static Logger logger = LoggerFactory.getLogger(SettingsController.class);

    @Autowired
    SettingsManagement sm;

    @Autowired
    AllDao dao;

    private ScoringUtils su = ScoringUtils.getInstance();
    private ClassificationUtils cu = ClassificationUtils.getInstance();

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

    @RequestMapping(value = "/scoring",method = RequestMethod.POST)
    public ResponseEntity<String> setScoringLimits(@RequestBody ScoringLimits newLimits)
    {
        logger.debug("setScoringLimits({})",newLimits);
        if (su.validateScoringLimits(newLimits))
        {
            logger.debug("ScoringLimits valid");
            sm.setScoringlimits(newLimits);
            dao.renewCache();
            ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.OK);
            return responseEntity;
        }
        else
        {
            logger.debug("ScoringLimits not valid");
            ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
    }

    @RequestMapping(value = "/classification",method = RequestMethod.POST)
    public ResponseEntity<String> setClassificationLimits(@RequestBody ClassificationLimits newLimits)
    {
        logger.debug("setClassificationLimits({})",newLimits);
        if (cu.validateClassificationLimits(newLimits))
        {
            logger.debug("ClassificationLimits valid");
            sm.setClassificationLimits(newLimits);
            dao.renewCache();
            ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.OK);
            return responseEntity;
        }
        else
        {
            logger.debug("ClassificationLimits not valid");
            ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
    }
}
