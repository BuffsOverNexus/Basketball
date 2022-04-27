package com.buffsovernexus.console.logging;

import com.buffsovernexus.entity.Team;

public class EngineLog {

    public static void possession(Team team) {
        ConsoleLog.format("[POSSESSION] %s has the ball.", team.getName().toUpperCase());
    }
}
