package com.kokakiwi.kintell.plugins.strike.client.display.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.kokakiwi.kintell.plugins.strike.client.display.Entity;

public class Bullet extends Entity
{
    public final static int WIDTH  = 3;
    public final static int HEIGHT = 3;
    
    public Bullet(String id)
    {
        super(id);
    }
    
    @Override
    public void render(Graphics g)
    {
        int x = (int) Math.floor(location.getX() - (WIDTH / 2));
        int y = (int) Math.floor(location.getY() - (HEIGHT / 2));
        
        g.setColor(Color.BLACK);
        g.fillOval(x, y, WIDTH, HEIGHT);
    }
}
