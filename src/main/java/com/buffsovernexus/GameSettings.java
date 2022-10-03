package com.buffsovernexus;

public class GameSettings {

    // Game Settings
    public final static int TOTAL_TEAMS = 8; // Total number of teams. Generally do not want to change this...
    public final static int TOTAL_GAMES_AGAINST = 2; // This is how many times teams have to play each other (multiples of 2)

    // Engine Settings
    public final static int GAME_POINTS_THRESHOLD = 20;

    // Guard Weights
    public final static int GUARD_FOUR_POINTER_MAXIMUM = 90;
    public final static int GUARD_TWO_POINTER_MAXIMUM = 80;
    public final static int GUARD_STEAL_MAXIMUM = 35;
    public final static int GUARD_BLOCK_MAXIMUM = 40;
    public final static int GUARD_REBOUND_MAXIMUM = 30;

    public final static int GUARD_FOUR_POINTER_MINIMUM = 20;
    public final static int GUARD_TWO_POINTER_MINIMUM = 40;
    public final static int GUARD_STEAL_MINIMUM = 5;
    public final static int GUARD_BLOCK_MINIMUM = 5;
    public final static int GUARD_REBOUND_MINIMUM = 10;

    // Forward Weights
    public final static int FORWARD_FOUR_POINTER_MAXIMUM = 80;
    public final static int FORWARD_TWO_POINTER_MAXIMUM = 80;
    public final static int FORWARD_STEAL_MAXIMUM = 25;
    public final static int FORWARD_BLOCK_MAXIMUM = 60;
    public final static int FORWARD_REBOUND_MAXIMUM = 70;

    public final static int FORWARD_FOUR_POINTER_MINIMUM = 10;
    public final static int FORWARD_TWO_POINTER_MINIMUM = 35;
    public final static int FORWARD_STEAL_MINIMUM = 0;
    public final static int FORWARD_BLOCK_MINIMUM = 20;
    public final static int FORWARD_REBOUND_MINIMUM = 30;

}
