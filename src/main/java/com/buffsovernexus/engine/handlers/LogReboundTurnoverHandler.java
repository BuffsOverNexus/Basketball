package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.console.logging.ConsoleLog;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.player.PlayerReboundTurnoverEvent;

public class LogReboundTurnoverHandler extends EngineEventHandler {

    @Override
    public void onPlayerReboundTurnoverEvent(PlayerReboundTurnoverEvent event) {
        try {
            ConsoleLog.format("(%s) %s lost the rebound to (%s) %s.", event.getLosingTeam().getName(), event.getLoser().getName(), event.getWinningTeam().getName(), event.getWinner().getName());
            Thread.sleep(2000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
