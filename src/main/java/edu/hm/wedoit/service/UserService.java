package edu.hm.wedoit.service;

import edu.hm.wedoit.model.User;

import java.util.List;

/**
 * Created by B3rni on 31.10.2015.
 */
public interface UserService
{
    public boolean loginOK(String username, String passwordHash);
    public boolean createUser(String username, String password);
    public boolean removeUser(String username);
    public User getUser(String username);
    public List<User> getAllUser();
}
