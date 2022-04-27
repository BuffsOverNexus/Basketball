package com.buffsovernexus.engine;

import com.buffsovernexus.engine.event.game.GameEndEvent;
import com.buffsovernexus.engine.event.game.GameStartEvent;
import com.buffsovernexus.engine.event.game.LeadChangeEvent;
import com.buffsovernexus.engine.event.game.PossessionChangeEvent;
import com.buffsovernexus.engine.event.player.*;

public interface EngineListener {

    // -- Player Events --
    void onPlayerShotScoredEvent(PlayerShotScoredEvent event);
    void onPlayerShotEvent(PlayerShotEvent event);
    void onPlayerStealEvent(PlayerStealEvent event);
    void onPlayerBlockEvent(PlayerBlockEvent event);

    // -- Game Events --
    void onGameStartEvent(GameStartEvent event);
    void onGameEndEvent(GameEndEvent event);
    void onPossessionChangeEvent(PossessionChangeEvent event);
    void onLeadChangeEvent(LeadChangeEvent event);

}
