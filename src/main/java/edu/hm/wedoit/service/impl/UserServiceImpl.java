package edu.hm.wedoit.service.impl;

import edu.hm.wedoit.model.User;
import edu.hm.wedoit.service.UserService;
import edu.hm.wedoit.usermanagement.UserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by B3rni on 01.11.2015.
 */

public class UserServiceImpl implements UserService
{
    @Autowired
    UserManagement userManagement;

    @Override
    public boolean loginOK(String username, String passwordHash)
    {
        System.out.println("UserServiceImpl LoginOK");
        return userManagement.loginOK(username,passwordHash);
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
