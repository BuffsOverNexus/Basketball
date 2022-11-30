package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.console.logging.ConsoleLog;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.game.GameEndEvent;
import com.buffsovernexus.entity.Game;

public class LogEndGameHandler extends EngineEventHandler {

    @Override
    public void onGameEndEvent(GameEndEvent event) {
        Game game = event.getGame();

        ConsoleLog.message("--- GAME END ---");
        ConsoleLog.format("%s [%s]  %s [%s]", game.getHome().getName(), game.getHomeScore(), game.getAway().getName(), game.getAwayScore());
        ConsoleLog.format("%s won by %s!", game.getWinner().getName(), Math.abs(game.getHomeScore() - game.getAwayScore()));
        ConsoleLog.message("-------------------------");
    }
}
