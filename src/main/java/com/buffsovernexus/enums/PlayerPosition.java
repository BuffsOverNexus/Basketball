package com.buffsovernexus.enums;

public enum PlayerPosition {
    NONE (0),
    GUARD (1),
    FORWARD (2);

    private int id;
    PlayerPosition(int id) {
        this.id = id;
    }

    int getId() {
        return id;
    }
}
