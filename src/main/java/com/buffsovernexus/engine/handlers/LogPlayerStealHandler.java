package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.console.logging.ConsoleLog;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.player.PlayerStealEvent;

public class LogPlayerStealHandler extends EngineEventHandler {

    @Override
    public void onPlayerStealEvent(PlayerStealEvent event) {
        try {
            ConsoleLog.format("(%s, %s) %s stole the ball from (%s, %s) %s.",
                    event.getThiefTeam().getName(),
                    event.getThief().getPosition().toString(),
                    event.getThief().getName(),
                    event.getStolenFromTeam().getName(),
                    event.getStolenFrom().getPosition().toString(),
                    event.getStolenFrom().getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
