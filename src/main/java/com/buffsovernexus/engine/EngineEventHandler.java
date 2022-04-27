package com.buffsovernexus.engine;


import com.buffsovernexus.engine.event.game.GameEndEvent;
import com.buffsovernexus.engine.event.game.GameStartEvent;
import com.buffsovernexus.engine.event.game.LeadChangeEvent;
import com.buffsovernexus.engine.event.game.PossessionChangeEvent;
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

    // Game Events
    @Override
    public void onGameStartEvent(GameStartEvent event) { }
    @Override
    public void onGameEndEvent(GameEndEvent event) { }
    @Override
    public void onPossessionChangeEvent(PossessionChangeEvent event) { }
    @Override
    public void onLeadChangeEvent(LeadChangeEvent event) { }
}
