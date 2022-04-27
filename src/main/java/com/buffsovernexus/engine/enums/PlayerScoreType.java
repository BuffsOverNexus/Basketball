package com.buffsovernexus.engine.enums;

public enum PlayerScoreType {
    MISSED (0),
    FOUR_POINTER (4),
    TWO_POINTER (2);

    private int amount;

    PlayerScoreType(int amount) {
        this.amount = amount;
    }
    public int getAmount() {
        return amount;
    }
}
