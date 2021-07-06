package com.buffsovernexus.engine;

import com.buffsovernexus.engine.EngineListener;
import com.buffsovernexus.engine.event.GuardScoreEvent;

public abstract class EngineEventHandler implements EngineListener {
    @Override
    public void onGuardScoreEvent(GuardScoreEvent event) { }
}
