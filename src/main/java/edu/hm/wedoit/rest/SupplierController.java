package edu.hm.wedoit.rest;

import edu.hm.wedoit.dao.AllDao;
import edu.hm.wedoit.model.Classification;
import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Semaphore;

/**
 * Created by B3rni on 24.10.2015.
 * This class is the interface for the frontend to handle all supplier concerning requests from the frontend
 */
@RestController
@RequestMapping("/api/supplier")
public class SupplierController implements Observer
{
    private final static Logger logger = LoggerFactory.getLogger(SupplierController.class);

    private Semaphore sem = new Semaphore(1);
    private AllDao.State newState;

    @Autowired
    AllDao allDao;

    @PostConstruct
    public void observeDao()
    {
        allDao.addObserver(this);
    }

    /**
     *
     * @return all suppliers with score
     */
    @RequestMapping("/all")
    public List<Supplier> getAllSuppliers()
    {
        logger.debug("getAllSuppliers()");
        return allDao.getAllSuppliers();
    }

    /**
     *
     * @return all suppliers with score
     */
    @RequestMapping("/all/{classification}")
    public List<Supplier> getAllSuppliersClassification(@PathVariable(value="classification") String classification)
    {
        logger.debug("getAllSuppliersClassification({})", classification);
        Classification c;
        try
        {
            c = Classification.valueOf(classification);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException();
        }

        return allDao.getAllSuppliersClassification(c);
    }


    /**
     *
     * @param id of the supplier
     * @return the supplier according to the supplier id
     */
    @RequestMapping("/{id}")
    public Supplier getSupplierById(@PathVariable(value="id") String id)
    {
        logger.debug("getSupplierById( + " + id + " )");
        return allDao.getSupplierById(id);
    }

    /**
     *
     * @param id of the supplier
     * @return all orders from a specific supplier
     */
    @RequestMapping("/{id}/orders")
    public List<Order> getOrdersForId(@PathVariable(value="id") String id)
    {
        logger.debug("getOrdersForId( + " + id + " )");
        return allDao.getAllOrdersForId(id);
    }

    /**
     * DDMMYYY - date pattern
     * @param from
     * @param to
     * @return suppliers with score based on a time interval
     */
    @RequestMapping("/all/between/{from}/{to}")
    public List<Supplier> getAllSuppliersDate(
            @PathVariable(value="from") String from,
            @PathVariable(value="to") String to)
    {
        logger.debug("getAllSuppliersDate( + " + from + ", " + to + " )");
        DateFormat format = new SimpleDateFormat("ddMMyyyy");
        Date fromDate;
        Date toDate;
        try
        {
            fromDate = new Date(format.parse(from).getTime());
            toDate = new Date(format.parse(to).getTime());
        }
        catch (ParseException e)
        {
            throw new IllegalArgumentException();
        }

        return allDao.getAllSuppliersDate(fromDate, toDate);
    }

    @RequestMapping("/all/{classification}/between/{from}/{to}")
    public List<Supplier> getAllSuppliersClassificationDate(
            @PathVariable(value="classification") String classification,
            @PathVariable(value="from") String from,
            @PathVariable(value="to") String to)
    {
        logger.debug("getAllSuppliersClassificationDate({}, {}, {})", classification, from, to);

        DateFormat format = new SimpleDateFormat("ddMMyyyy");
        Date fromDate;
        Date toDate;
        Classification c;
        try
        {
            fromDate = new Date(format.parse(from).getTime());
            toDate = new Date(format.parse(to).getTime());
            c = Classification.valueOf(classification);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException();
        }

        return allDao.getAllSuppliersClassificationDate(c, fromDate, toDate);
    }

    /**
     * Returns the state of the connection to the database
     * Blocks if the connection isn't changing
     * Can be used for long polling requests
     * @return The state of the connection to the database
     */
    @RequestMapping("/connection")
    public AllDao.State getConnectionInfo()
    {
        logger.debug("getConnectionInfo()");
        while(true)
        {
            try
            {
                sem.acquire();
                return allDao.getState();
            }
            catch (InterruptedException ie)
            {
                return AllDao.State.CONNECTED;
            }
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        AllDao dao = (AllDao) o;
        if(newState != dao.getState())
        {
            newState = dao.getState();
            sem.release();
        }
    }

    @PreDestroy
    void releaseDao()
    {
        allDao.deleteObserver(this);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAppException(IllegalArgumentException ex)
    {
        return ex.getMessage();
    }
}
