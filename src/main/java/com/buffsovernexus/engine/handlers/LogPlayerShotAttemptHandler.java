package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.console.logging.ConsoleLog;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.player.PlayerShotEvent;
import com.buffsovernexus.entity.Team;

import java.io.Console;

public class LogPlayerShotAttemptHandler extends EngineEventHandler {

    @Override
    public void onPlayerShotEvent(PlayerShotEvent event) {
        try {
            ConsoleLog.format("(%s, %s) %s is attempting a shot a %s", event.getShootingTeam().getName(), event.getShooter().getPosition().toString(), event.getShooter().getName(), event.getPlayerShotType().toString());
            Thread.sleep(1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
