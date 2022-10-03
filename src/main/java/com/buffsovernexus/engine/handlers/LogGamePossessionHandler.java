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
            ConsoleLog.format("< [%s] %s - %s [%s] >", game.getHome().getName(), game.getHomeScore(), game.getAway().getName(), game.getAwayScore());
            ConsoleLog.emptyLine();
            ConsoleLog.format(">> [ %s has the ball ] << ", event.getOffense().getName());
            ConsoleLog.emptyLine();
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
