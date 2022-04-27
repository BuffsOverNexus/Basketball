package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.console.logging.ConsoleLog;
import com.buffsovernexus.console.logging.EngineLog;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.game.GameStartEvent;
import com.buffsovernexus.entity.Game;

public class LogStartGameHandler extends EngineEventHandler {

    @Override
    public void onGameStartEvent(GameStartEvent event) {
        Game game = event.getGame();
        // Log the game's stats
        ConsoleLog.message("--- GAME START ---");
        ConsoleLog.format("HOME [%s] vs AWAY [%s]", game.getHome().getName(), game.getAway().getName());
        ConsoleLog.message("-------------------------");
        EngineLog.possession( event.getTeamWithPossession() );
    }
}
