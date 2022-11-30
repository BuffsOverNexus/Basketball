package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.console.logging.ConsoleLog;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.game.GamePossessionChangeEvent;
import com.buffsovernexus.entity.Game;

public class LogGamePossessionHandler extends EngineEventHandler {

    @Override
    public void onGamePossessionChangeEvent(GamePossessionChangeEvent event) {
        try {
            Game game = event.getGame();

            ConsoleLog.emptyLine();
            ConsoleLog.format("< [%s] %s - %s [%s] >", game.getHome().getName(), game.getHomeScore(), game.getAwayScore(), game.getAway().getName());
            ConsoleLog.emptyLine();
            ConsoleLog.format(">> [ %s have the ball ] << ", event.getOffense().getName());
            ConsoleLog.emptyLine();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
