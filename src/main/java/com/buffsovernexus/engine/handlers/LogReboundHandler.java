package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.console.logging.ConsoleLog;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.player.PlayerReboundEvent;

public class LogReboundHandler extends EngineEventHandler {

    @Override
    public void onPlayerReboundEvent(PlayerReboundEvent event) {
        try {
            ConsoleLog.format("(%s) %s rebounded the ball. ", event.getReboundingTeam().getName(), event.getRebounder().getName());
            Thread.sleep(1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
