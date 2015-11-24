package edu.hm.wedoit.rest;

import edu.hm.wedoit.dao.AllDao;
import edu.hm.wedoit.model.Order;
import edu.hm.wedoit.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        System.out.println("getAllSuppliers Rest");
        return allDao.getAllSuppliersWithScore();
    }

    /**
     *
     * @param id of the supplier
     * @return the supplier according to the supplier id
     */
    @RequestMapping("/{id}")
    public Supplier getSupplierById(@PathVariable(value="id") String id)
    {
        System.out.println("getSupplierById " + id + " Rest");
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
        System.out.println("getOrdersForId " + id + " Rest");
        return allDao.getAllOrdersForId(id);
    }

    /**
     * DDMMYYY - date pattern
     * @param from
     * @param to
     * @return suppliers with score based on a time interval
     */
    @RequestMapping("/all/between/{from}/{to}")
    public List<Supplier> getAllSuppliersDate(@PathVariable(value="from") String from, @PathVariable(value="to") String to)
    {
        System.out.println("getAllSuppliersDate Rest");
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
            return null;
        }

        return allDao.getAllSuppliersDate(fromDate, toDate);
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
        System.out.println("getConnectionInfo Rest");
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
}
