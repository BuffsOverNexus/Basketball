package com.buffsovernexus.engine;

import com.buffsovernexus.engine.event.GuardScoreEvent;

public interface EngineListener {

    void onGuardScoreEvent(GuardScoreEvent event);
}
