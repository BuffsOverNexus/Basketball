package com.buffsovernexus.engine;

import com.buffsovernexus.engine.event.game.GameEndEvent;
import com.buffsovernexus.engine.event.game.GameLeadChangeEvent;
import com.buffsovernexus.engine.event.game.GameStartEvent;
import com.buffsovernexus.engine.event.game.GamePossessionChangeEvent;
import com.buffsovernexus.engine.event.player.*;

public interface EngineListener {

    // -- Player Events --
    void onPlayerShotScoredEvent(PlayerShotScoredEvent event);
    void onPlayerShotEvent(PlayerShotEvent event);
    void onPlayerStealEvent(PlayerStealEvent event);
    void onPlayerBlockEvent(PlayerBlockEvent event);
    void onPlayerShotMissedEvent(PlayerShotMissedEvent event);
    void onPlayerReboundEvent(PlayerReboundEvent event);
    void onPlayerReboundTurnoverEvent(PlayerReboundTurnoverEvent event);
    void onPlayerPassEvent(PlayerPassEvent event);

    // -- Game Events --
    void onGameStartEvent(GameStartEvent event);
    void onGameEndEvent(GameEndEvent event);
    void onGamePossessionChangeEvent(GamePossessionChangeEvent event);
    void onGameLeadChangeEvent(GameLeadChangeEvent event);

}
