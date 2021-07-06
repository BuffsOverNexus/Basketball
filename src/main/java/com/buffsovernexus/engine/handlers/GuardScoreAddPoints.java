package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.GuardScoreEvent;

public class GuardScoreAddPoints extends EngineEventHandler {
    @Override
    public void onGuardScoreEvent(GuardScoreEvent event) {
        System.out.println(event.getGuard().getFirstName());
    }
}
