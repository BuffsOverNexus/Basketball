package com.buffsovernexus.engine.helpers;

public class WeightedReward {

    private int weight;
    private boolean reward;

    public WeightedReward(int weight, boolean reward) {
        this.weight = weight;
        this.reward = reward;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setReward(boolean reward) {
        this.reward = reward;
    }

    public boolean getReward() {
        return reward;
    }
}
