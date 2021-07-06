package com.buffsovernexus.engine;

import com.buffsovernexus.engine.enums.PlayerScoreType;
import com.buffsovernexus.engine.event.GuardScoreEvent;
import com.buffsovernexus.engine.handlers.GuardScoreAddPoints;
import com.buffsovernexus.entity.Guard;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    private List<EngineListener> engineListeners = new ArrayList<>();

    public Engine() {
        // Add Handlers To Engine
        addEventHandler(new GuardScoreAddPoints());
    }

    public void test() {
        Guard guard = new Guard("Ryan", "Goldberg", 10);
        for (EngineListener engineListener : engineListeners) {
            GuardScoreEvent guardScoreEvent = new GuardScoreEvent(guard, PlayerScoreType.FOUR_POINTER);
            engineListener.onGuardScoreEvent(guardScoreEvent);
        }
    }

    public void addEventHandler(EngineListener engineListener) {
        this.engineListeners.add(engineListener);
    }


}
