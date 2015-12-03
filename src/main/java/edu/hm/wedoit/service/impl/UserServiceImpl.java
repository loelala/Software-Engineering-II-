package edu.hm.wedoit.service.impl;

import edu.hm.wedoit.model.User;
import edu.hm.wedoit.service.UserService;
import edu.hm.wedoit.usermanagement.UserManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;


/**
 * Implementation of the UserService
 * see {@link edu.hm.wedoit.service.UserService }
 */
public class UserServiceImpl implements UserService
{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserManagement userManagement;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean loginOK(String username, String passwordHash)
    {
        logger.debug("loginOK ({}, {})",username, passwordHash);
        return userManagement.loginOK(username, passwordHash);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean createUser(String username, String password, String email, String surname)
    {
        logger.debug("createUser ({}, {})",username, password);
        return userManagement.createUser(username, password, email, surname);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeUser(String username)
    {
        logger.debug("removeUser ({})",username);
        return userManagement.removeUser(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(String username)
    {
        logger.debug("getUser ({})",username);
        throw new NotImplementedException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAllUser()
    {
        logger.debug("getAllUser ()");
        return userManagement.getAllUser();
    }
}
