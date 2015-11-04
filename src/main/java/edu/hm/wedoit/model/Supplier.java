package edu.hm.wedoit.model;

import java.io.Serializable;

/**
 * Created by B3rni on 24.10.2015.
 */
public class Supplier implements Serializable
{
    private String name;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    private String id;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
