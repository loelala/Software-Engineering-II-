package edu.hm.wedoit.service.impl;

import edu.hm.wedoit.model.User;
import edu.hm.wedoit.service.UserService;
import edu.hm.wedoit.usermanagement.UserManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by B3rni on 01.11.2015.
 */

public class UserServiceImpl implements UserService
{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserManagement userManagement;

    @Override
    public boolean loginOK(String username, String passwordHash)
    {
        logger.debug("LoginOK - received a login request user: {} password: {}",username,passwordHash);
        return userManagement.loginOK(username, passwordHash);
    }

    @Override
    public boolean createUser(String username, String password)
    {
        return false;
    }

    @Override
    public boolean removeUser(String username)
    {
        return false;
    }

    @Override
    public User getUser(String username)
    {
        return null;
    }
}
