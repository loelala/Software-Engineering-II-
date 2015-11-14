package edu.hm.wedoit.service;

import edu.hm.wedoit.model.User;

import java.util.List;

/**
 * Created by B3rni on 31.10.2015.
 */
public interface UserService
{
    /**
     * passes the login request to the business logic
     * @param username
     * @param passwordHash
     * @return wether the login is ok or not
     */
    public boolean loginOK(String username, String passwordHash);

    /**
     * passes the user creation request to the business logic
     * @param username
     * @param password
     * @return wether the user was created or not
     */
    public boolean createUser(String username, String password);

    /**
     * passes the user deletion request to the business logic
     * @param username
     * @return wether the user was deleted or ot
     */
    public boolean removeUser(String username);
    public User getUser(String username);

    /**
     * passes the request to get a list of all users to the business logic
     * @return list of all users in the system
     */
    public List<User> getAllUser();
}
