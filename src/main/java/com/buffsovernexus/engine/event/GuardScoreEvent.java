package com.buffsovernexus.engine.event;

import com.buffsovernexus.engine.enums.PlayerScoreType;
import com.buffsovernexus.entity.Guard;

public class GuardScoreEvent {
    private Guard guard;
    private PlayerScoreType playerScoreType;

    public GuardScoreEvent(Guard guard, PlayerScoreType playerScoreType) {
        this.setGuard(guard);
        this.setPlayerScoreType(playerScoreType);
    }

    public Guard getGuard() {
        return guard;
    }

    public void setGuard(Guard guard) {
        this.guard = guard;
    }

    public PlayerScoreType getPlayerScoreType() {
        return playerScoreType;
    }

    public void setPlayerScoreType(PlayerScoreType playerScoreType) {
        this.playerScoreType = playerScoreType;
    }
}
