package com.kokakiwi.kintell.plugins.strike.client;

import com.kokakiwi.kintell.client.plugins.ClientPlugin;

public class KintellStrikeBoardClientPlugin extends ClientPlugin
{
    private StrikeBoardFactory boardFactory;
    
    @Override
    public void onEnable()
    {
        boardFactory = new StrikeBoardFactory(this);
        
        getCore().registerBoardFactory(boardFactory);
    }
    
    public StrikeBoardFactory getBoardFactory()
    {
        return boardFactory;
    }
    
}
