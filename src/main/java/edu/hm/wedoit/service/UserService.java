package edu.hm.wedoit.service;

import edu.hm.wedoit.model.User;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Service for Usermanagement
 */
public interface UserService
{
    /**
     * passes the login request to the business logic
     * @param username
     * @param passwordHash
     * @return wether the login is ok or not
     */
    boolean loginOK(String username, String passwordHash);

    /**
     * passes the user creation request to the business logic
     * @param username
     * @param password
     * @return wether the user was created or not
     */
    boolean createUser(String username, String password, String email, String surname);

    /**
     * passes the user deletion request to the business logic
     * @param username
     * @return wether the user was deleted or ot
     */
    boolean removeUser(String username);

    /**
     * gets the user
     * @param username
     * @return the User-Object for the username
     */
    User getUser(String username);

    /**
     * passes the request to get a list of all users to the business logic
     * @return list of all users in the system
     */
    List<User> getAllUser();
}
