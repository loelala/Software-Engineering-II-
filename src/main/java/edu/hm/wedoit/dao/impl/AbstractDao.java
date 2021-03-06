package edu.hm.wedoit.dao.impl;

import edu.hm.wedoit.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Observable;

/**
 * implements the basedao interface, we don't have to do it in every upcoming dao
 */
public abstract class AbstractDao extends Observable implements BaseDao

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
