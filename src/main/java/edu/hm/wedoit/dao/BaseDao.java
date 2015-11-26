package edu.hm.wedoit.dao;


import javax.sql.DataSource;

/**
 * setting up datasource for all daos
 */
public interface BaseDao
{
    void setDataSource(DataSource dataSource);
}
