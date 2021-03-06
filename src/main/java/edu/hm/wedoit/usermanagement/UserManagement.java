package edu.hm.wedoit.usermanagement;

import edu.hm.wedoit.model.User;

import javax.jws.soap.SOAPBinding;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Interface to manage the users
 */
public interface UserManagement
{
    /**
     * Checks wether login is ok or not
     * @param username
     * @param passwordHash
     * @return true if login is ok, false otherwise
     */
    boolean loginOK(String username, String passwordHash);

    /**
     * creates a new user and saves it
     * @param username
     * @param password
     * @return true if user was created
     */
    boolean createUser(String username, String password, String email, String surname);

    /**
     * deletes a user
     * @param username
     * @return true if the user could be deleted
     */
    boolean removeUser(String username);

    /**
     * edit a user
     * @param user user to edit
     * @return true if the user could be edited
     */
    boolean editUser(User user);

    /**
     * gets the username
     * @param username
     * @return the user
     */
    User getUser(String username);

    /**
     * Returns a list of all users
     * @return list with all users
     */
    List<User> getAllUser();

}
