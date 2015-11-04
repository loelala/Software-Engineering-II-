package edu.hm.wedoit.model;

import java.io.Serializable;

/**
 * Created by B3rni on 31.10.2015.
 */
public class User implements Serializable
{
    private String name;
    private String password;

    public User(String name, String password)
    {
        this.name = name;
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }


}
