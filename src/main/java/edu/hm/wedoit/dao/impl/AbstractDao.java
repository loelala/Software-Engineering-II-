package edu.hm.wedoit.dao.impl;

import edu.hm.wedoit.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by B3rni on 25.10.2015.
 */
public class AbstractDao implements BaseDao
{
    @Autowired
    private DataSource dataSource;

    protected JdbcTemplate jdbcTemplate;

    @Override
    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }
}
