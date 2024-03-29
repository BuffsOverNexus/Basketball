package com.buffsovernexus.engine;


import com.buffsovernexus.engine.event.game.GameEndEvent;
import com.buffsovernexus.engine.event.game.GameLeadChangeEvent;
import com.buffsovernexus.engine.event.game.GameStartEvent;
import com.buffsovernexus.engine.event.game.GamePossessionChangeEvent;
import com.buffsovernexus.engine.event.player.*;

/**
 * This parent class ensures that all events are handled.
 * However, not all events may have a proper handler to record them.
 */
public abstract class EngineEventHandler implements EngineListener {
    // Player Events
    @Override
    public void onPlayerShotScoredEvent(PlayerShotScoredEvent event) { }
    @Override
    public void onPlayerShotEvent(PlayerShotEvent event) { }
    @Override
    public void onPlayerStealEvent(PlayerStealEvent event) { }
    @Override
    public void onPlayerBlockEvent(PlayerBlockEvent event) { }
    @Override
    public void onPlayerShotMissedEvent(PlayerShotMissedEvent event) { }
    @Override
    public void onPlayerPassEvent(PlayerPassEvent event) { }
    @Override
    public void onPlayerReboundEvent(PlayerReboundEvent event) {}
    @Override
    public void onPlayerReboundTurnoverEvent(PlayerReboundTurnoverEvent event) { }

    // Game Events
    @Override
    public void onGameStartEvent(GameStartEvent event) { }
    @Override
    public void onGameEndEvent(GameEndEvent event) { }
    @Override
    public void onGamePossessionChangeEvent(GamePossessionChangeEvent event) { }
    @Override
    public void onGameLeadChangeEvent(GameLeadChangeEvent event) { }
}
