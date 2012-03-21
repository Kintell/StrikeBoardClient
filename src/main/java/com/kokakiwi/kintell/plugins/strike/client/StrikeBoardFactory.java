package com.kokakiwi.kintell.plugins.strike.client;

import java.util.List;

import com.kokakiwi.kintell.client.core.KintellClientCore;
import com.kokakiwi.kintell.client.core.board.BoardFactory;
import com.kokakiwi.kintell.spec.net.msg.ProgramsListMessage.Program;

public class StrikeBoardFactory implements BoardFactory<StrikeBoard>
{
    private final KintellStrikeBoardClientPlugin plugin;
    
    public StrikeBoardFactory(KintellStrikeBoardClientPlugin plugin)
    {
        this.plugin = plugin;
    }
    
    public String getId()
    {
        return "strike";
    }
    
    public String getName()
    {
        return "Striker killmatch";
    }
    
    public StrikeBoard createBoard(KintellClientCore core,
            List<Program> programs, int id)
    {
        StrikeBoard board = new StrikeBoard(plugin, core, programs, id);
        
        return board;
    }
    
}
