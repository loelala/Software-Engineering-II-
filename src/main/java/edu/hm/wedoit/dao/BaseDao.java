package edu.hm.wedoit.dao;


import javax.sql.DataSource;

/**
 * Created by B3rni on 24.10.2015.
 * setting up datasource for all daos
 */
public interface BaseDao
{
    public void setDataSource(DataSource dataSource);
}
