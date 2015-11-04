package edu.hm.wedoit.dao;

import edu.hm.wedoit.model.Supplier;
import edu.hm.wedoit.model.SupplierWithScore;

import java.util.List;

/**
 * Created by Joncn on 02.11.2015.
 */
public interface SupplierWithScoreDao
{
    public List<SupplierWithScore> getAllSuppliersWithScore();
}
