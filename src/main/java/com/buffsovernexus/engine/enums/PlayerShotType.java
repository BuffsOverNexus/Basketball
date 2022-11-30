package com.buffsovernexus.engine.enums;

public enum PlayerShotType {
    FOUR_POINTER (4),
    TWO_POINTER (2);

    private int points;
    PlayerShotType(int points) {
        this.points = points;
    }
    public int getPoints() {
        return points;
    }
}
