package edu.hm.wedoit.model;

import java.io.Serializable;

/**
 * One User for this application
 */
public class User implements Serializable
{
    private String name;
    private String password;
    private String email;
    private String surname;

    public User(String name, String password, String email, String surname)
    {
        this.name = name;
        this.password = password;
        this.email = email;
        this.surname = surname;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
