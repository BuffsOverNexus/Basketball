package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.console.logging.ConsoleLog;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.player.PlayerStealEvent;

public class LogPlayerStealHandler extends EngineEventHandler {

    @Override
    public void onPlayerStealEvent(PlayerStealEvent event) {
        try {
            ConsoleLog.format("(%s) %s stole the ball from (%s) %s.", event.getThiefTeam().getName(), event.getThief().getName(), event.getStolenFromTeam().getName(), event.getStolenFrom().getName());
            Thread.sleep(1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
