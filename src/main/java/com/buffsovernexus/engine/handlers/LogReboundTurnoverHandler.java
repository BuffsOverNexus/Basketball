package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.console.logging.ConsoleLog;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.player.PlayerReboundTurnoverEvent;

public class LogReboundTurnoverHandler extends EngineEventHandler {

    @Override
    public void onPlayerReboundTurnoverEvent(PlayerReboundTurnoverEvent event) {
        try {
            ConsoleLog.format("(%s, %s) %s lost the rebound to (%s, %s) %s.",
                    event.getLosingTeam().getName(),
                    event.getLoser().getPosition().toString(),
                    event.getLoser().getName(),
                    event.getWinningTeam().getName(),
                    event.getWinner().getPosition().toString(),
                    event.getWinner().getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
