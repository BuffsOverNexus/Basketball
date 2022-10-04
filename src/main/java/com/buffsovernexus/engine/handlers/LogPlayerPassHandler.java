package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.console.logging.ConsoleLog;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.player.PlayerPassEvent;

public class LogPlayerPassHandler extends EngineEventHandler {

    @Override
    public void onPlayerPassEvent(PlayerPassEvent event) {
        try {
            ConsoleLog.format("(%s, %s) %s passed the ball to (%s, %s) %s.",
                    event.getPassingTeam().getName(),
                    event.getPasser().getPosition().toString(),
                    event.getPasser().getName(),
                    event.getPassingTeam().getName(),
                    event.getReceivingPlayer().getPosition().toString(),
                    event.getReceivingPlayer().getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
