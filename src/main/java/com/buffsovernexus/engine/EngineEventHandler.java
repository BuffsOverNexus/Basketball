package com.buffsovernexus.engine;

import com.buffsovernexus.engine.event.GuardScoreEvent;
import org.apache.commons.lang.NotImplementedException;


public abstract class EngineEventHandler implements EngineListener {
    @Override
    public void onGuardScoreEvent(GuardScoreEvent event) {
        throw new NotImplementedException("GuardScoreEvent has no listeners.");
    }
}
