package com.kokakiwi.kintell.plugins.strike.client.display;

import com.kokakiwi.kintell.spec.utils.data.DataBuffer;
import com.kokakiwi.kintell.spec.utils.data.Encodable;

public class Location implements Encodable
{
    private double x;
    private double y;
    
    private float  angle;
    
    public Location()
    {
        this(0, 0, 0);
    }
    
    public Location(double x, double y, float angle)
    {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
    
    public double getX()
    {
        return x;
    }
    
    public void setX(double x)
    {
        this.x = x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    public float getAngle()
    {
        return angle;
    }
    
    public void setAngle(float angle)
    {
        this.angle = angle;
    }
    
    public void encode(DataBuffer buf)
    {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeFloat(angle);
    }
    
    public void decode(DataBuffer buf)
    {
        x = buf.readDouble();
        y = buf.readDouble();
        angle = buf.readFloat();
    }
}
