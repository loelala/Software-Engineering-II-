package edu.hm.wedoit.usermanagement;

import edu.hm.wedoit.model.User;

/**
 * Created by B3rni on 01.11.2015.
 */
public interface UserManagement
{
    public boolean loginOK(String username, String passwordHash);
    public boolean createUser(String username, String password);
    public boolean removeUser(String username);
    public User getUser(String username);
}
