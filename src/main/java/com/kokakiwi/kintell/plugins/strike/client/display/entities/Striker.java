package com.kokakiwi.kintell.plugins.strike.client.display.entities;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import com.kokakiwi.kintell.plugins.strike.client.StrikeBoard;
import com.kokakiwi.kintell.plugins.strike.client.display.Entity;
import com.kokakiwi.kintell.spec.net.msg.ProgramsListMessage.Program;

public class Striker extends Entity
{
    public final static int WIDTH   = 32;
    public final static int HEIGHT  = 32;
    public final static int MAXLIFE = 100;
    
    private final Program   program;
    
    private int             life    = MAXLIFE;
    
    public Striker(Program program)
    {
        super(program.getId());
        this.program = program;
    }
    
    public Program getProgram()
    {
        return program;
    }
    
    public int getLife()
    {
        return life;
    }
    
    public void setLife(int life)
    {
        this.life = life;
    }
    
    @Override
    public void render(Graphics g)
    {
        System.out.println();
        
        final int x = (int) Math.floor(location.getX());
        final int y = (int) Math.floor(location.getY());
        
        g.setColor(Color.black);
        g.drawOval(x, y, WIDTH, HEIGHT);
        
        int x0 = (int) (x + WIDTH / 2 + WIDTH / 2
                * Math.cos(Math.toRadians(location.getAngle())));
        int y0 = (int) (y + HEIGHT / 2 + WIDTH / 2
                * Math.sin(Math.toRadians(location.getAngle())));
        final int x1 = (int) (x + WIDTH / 2 + (WIDTH / 2 + 10)
                * Math.cos(Math.toRadians(location.getAngle())));
        final int y1 = (int) (y + HEIGHT / 2 + (WIDTH / 2 + 10)
                * Math.sin(Math.toRadians(location.getAngle())));
        
        g.drawLine(x0, y0, x1, y1);
        
        final int cx = (int) Math.floor(location.getX() + WIDTH / 2);
        
        final FontMetrics metrics = g.getFontMetrics();
        
        final int textWidth = metrics.charsWidth(id.toCharArray(), 0,
                id.length());
        int tx = cx - textWidth / 2;
        
        if (tx < 3)
        {
            tx = 3;
        }
        if (tx + textWidth > StrikeBoard.WIDTH - 3)
        {
            tx = StrikeBoard.WIDTH - textWidth - 3;
        }
        
        int ty = y - 20;
        
        if (ty < metrics.getHeight())
        {
            ty = y + HEIGHT + 15;
        }
        
        g.drawString(id, tx, ty);
        
        x0 = cx - 20;
        y0 = ty + 10;
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x0, y0, 40, 5);
        
        final int width = life * 40 / MAXLIFE;
        
        g.setColor(Color.RED);
        g.fillRect(x0, y0, width, 5);
    }
}
