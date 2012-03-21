package com.kokakiwi.kintell.plugins.strike.client.display;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import com.kokakiwi.kintell.plugins.strike.client.StrikeBoard;

public class StrikeComponent extends Component implements Runnable
{
    private static final long serialVersionUID = -4923726506283410369L;
    
    private final StrikeBoard   board;
    
    private String            winner           = null;
    private boolean           running          = true;
    
    public StrikeComponent(StrikeBoard board)
    {
        super();
        this.board = board;
        setSize(new Dimension(StrikeBoard.WIDTH, StrikeBoard.HEIGHT));
    }
    
    @Override
    public void update(Graphics g)
    {
        paint(g);
    }
    
    @Override
    public void paint(Graphics g)
    {
        try
        {
            board.getSemaphore().acquire();
            for (Entity entity : board.getEntities().values())
            {
                entity.render(g);
            }
            
            for (String id : board.getRemoved())
            {
                board.getEntities().remove(id);
            }
            board.getRemoved().clear();
            board.getSemaphore().release();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        
        if (winner != null)
        {
            String text = "WINNER : " + winner;
            float size = 40.0f;
            
            Font font = new Font("Ubuntu", Font.BOLD, (int) Math.floor(size));
            
            g.setFont(font);
            g.setColor(Color.BLACK);
            
            FontMetrics metrics = g.getFontMetrics();
            
            int x = (StrikeBoard.WIDTH / 2)
                    - (metrics.charsWidth(text.toCharArray(), 0, text.length()) / 2);
            int y = (int) ((StrikeBoard.HEIGHT / 2) - Math.floor(size / 2));
            
            g.drawString(text, x, y);
        }
    }
    
    public boolean isRunning()
    {
        return running;
    }
    
    public void setRunning(boolean running)
    {
        this.running = running;
    }
    
    public void run()
    {
        while (running)
        {
            repaint();
            try
            {
                Thread.sleep(10L);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void win(String id)
    {
        winner = id;
    }
}
