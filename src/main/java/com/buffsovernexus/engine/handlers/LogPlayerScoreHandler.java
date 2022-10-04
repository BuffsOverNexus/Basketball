package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.console.logging.ConsoleLog;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.player.PlayerShotScoredEvent;
import com.buffsovernexus.entity.Player;

public class LogPlayerScoreHandler extends EngineEventHandler {

    @Override
    public void onPlayerShotScoredEvent(PlayerShotScoredEvent event) {
        try {
            Player scorer = event.getScorer();
            ConsoleLog.format("(%s, %s) %s scored %s points!", event.getScoringTeam().getName(), scorer.getPosition().toString(), scorer.getName(), event.getPlayerShotType().getPoints());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
