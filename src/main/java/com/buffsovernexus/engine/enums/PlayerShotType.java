package com.buffsovernexus.engine.enums;

public enum PlayerShotType {
    MISSED (0),
    SCORED (1),
    OUT_OF_BOUNDS (2);

    private int id;
    PlayerShotType(int id) {
        this.id = id;
    }
    int getId() {
        return id;
    }
}
