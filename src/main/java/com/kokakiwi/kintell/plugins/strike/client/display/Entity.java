package com.kokakiwi.kintell.plugins.strike.client.display;

import java.awt.Graphics;

public abstract class Entity
{
    protected final String id;
    protected Location     location = new Location();
    
    public Entity(String id)
    {
        this.id = id;
    }
    
    public String getId()
    {
        return id;
    }
    
    public Location getLocation()
    {
        return location;
    }
    
    public void setLocation(Location location)
    {
        this.location = location;
    }
    
    public abstract void render(Graphics g);
}
