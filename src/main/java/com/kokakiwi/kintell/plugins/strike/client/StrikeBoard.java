package com.kokakiwi.kintell.plugins.strike.client;

import java.awt.Component;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kokakiwi.kintell.client.core.KintellClientCore;
import com.kokakiwi.kintell.client.core.board.Board;
import com.kokakiwi.kintell.client.core.board.Listener;
import com.kokakiwi.kintell.plugins.strike.client.display.Entity;
import com.kokakiwi.kintell.plugins.strike.client.display.Location;
import com.kokakiwi.kintell.plugins.strike.client.display.StrikeComponent;
import com.kokakiwi.kintell.plugins.strike.client.display.entities.Bullet;
import com.kokakiwi.kintell.plugins.strike.client.display.entities.Striker;
import com.kokakiwi.kintell.plugins.strike.client.net.RemoveEntity;
import com.kokakiwi.kintell.plugins.strike.client.net.SpawnEntity;
import com.kokakiwi.kintell.plugins.strike.client.net.UpdateEntity;
import com.kokakiwi.kintell.plugins.strike.client.net.Win;
import com.kokakiwi.kintell.spec.net.msg.BoardMessage;
import com.kokakiwi.kintell.spec.net.msg.ProgramsListMessage.Program;

public class StrikeBoard extends Board implements Listener
{
    public final static int                      WIDTH     = 1024;
    public final static int                      HEIGHT    = WIDTH * 3 / 4;
    
    private final KintellStrikeBoardClientPlugin plugin;
    private StrikeComponent                      component = null;
    
    private final Map<String, Striker>           strikers  = Maps.newLinkedHashMap();
    private final Map<String, Entity>            entities  = Maps.newLinkedHashMap();
    private final List<String>                   removed   = Lists.newLinkedList();
    
    private final Semaphore                      semaphore = new Semaphore(1);
    
    public StrikeBoard(KintellStrikeBoardClientPlugin plugin,
            KintellClientCore core, List<Program> programs, int id)
    {
        super(core, programs, id);
        this.plugin = plugin;
        core.registerListener(id, this);
        
        for (final Program program : programs)
        {
            final Striker striker = new Striker(program);
            striker.setLocation(new Location());
            
            strikers.put(program.getId(), striker);
            entities.put(program.getId(), striker);
        }
    }
    
    @Override
    public Component getComponent()
    {
        if (component == null)
        {
            component = new StrikeComponent(this);
            
            final Thread t = new Thread(component);
            t.start();
        }
        
        return component;
    }
    
    public KintellStrikeBoardClientPlugin getPlugin()
    {
        return plugin;
    }
    
    public Map<String, Striker> getStrikers()
    {
        return strikers;
    }
    
    public Map<String, Entity> getEntities()
    {
        return entities;
    }
    
    public List<String> getRemoved()
    {
        return removed;
    }
    
    public Semaphore getSemaphore()
    {
        return semaphore;
    }
    
    @Override
    public void start()
    {
        
    }
    
    @Override
    public void stop()
    {
        component.setRunning(false);
    }
    
    public void messageReceived(BoardMessage msg)
    {
        if (msg.getOpcode().equalsIgnoreCase("updateEntity"))
        {
            try
            {
                semaphore.acquire();
                
                final UpdateEntity packet = new UpdateEntity();
                packet.decode(msg.getData());
                
                final Entity entity = entities.get(packet.getId());
                if (entity != null)
                {
                    entity.setLocation(packet.getLocation());
                    if (entity instanceof Striker
                            && packet.getDatas().containsKey("life"))
                    {
                        final int life = Integer.valueOf(packet.getDatas().get(
                                "life"));
                        ((Striker) entity).setLife(life);
                    }
                }
                
                semaphore.release();
            }
            catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
            
        }
        else if (msg.getOpcode().equalsIgnoreCase("spawnEntity"))
        {
            try
            {
                semaphore.acquire();
                
                final SpawnEntity packet = new SpawnEntity();
                packet.decode(msg.getData());
                
                if (packet.getType().equalsIgnoreCase("bullet"))
                {
                    final Bullet bullet = new Bullet(packet.getId());
                    entities.put(bullet.getId(), bullet);
                }
                
                semaphore.release();
            }
            catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        else if (msg.getOpcode().equalsIgnoreCase("removeEntity"))
        {
            try
            {
                semaphore.acquire();
                
                final RemoveEntity packet = new RemoveEntity();
                packet.decode(msg.getData());
                
                removed.add(packet.getId());
                
                semaphore.release();
            }
            catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        else if (msg.getOpcode().equalsIgnoreCase("win"))
        {
            final Win packet = new Win(null);
            packet.decode(msg.getData());
            
            component.win(packet.getId());
        }
    }
    
}
