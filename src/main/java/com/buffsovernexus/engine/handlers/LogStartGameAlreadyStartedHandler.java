package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.console.logging.ConsoleLog;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.game.GameStartEvent;
import com.buffsovernexus.entity.Game;

public class LogStartGameAlreadyStartedHandler extends EngineEventHandler {

    @Override
    public void onGameStartEvent(GameStartEvent event) {
        Game game = event.getGame();
        if (game.getAwayScore() > 0 || game.getHomeScore() > 0) {
            ConsoleLog.format("!! RESUMING GAME !!");
        }
    }
}
