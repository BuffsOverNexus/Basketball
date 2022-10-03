package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.console.logging.ConsoleLog;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.player.PlayerPassEvent;

public class LogPlayerPassHandler extends EngineEventHandler {

    @Override
    public void onPlayerPassEvent(PlayerPassEvent event) {
        try {
            ConsoleLog.format("(%s) %s passed the ball to %s.", event.getPassingTeam().getName(), event.getPasser().getName(), event.getReceivingPlayer().getName());
            Thread.sleep(1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
