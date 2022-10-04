package com.buffsovernexus;

public class GameWeights {
    // Calculator will automatically add in remaining buffer depending on what you insert

    public final static int ABSOLUTE = 100;

    public final static int GUARD_FOUR_POINTER_ATTEMPT_WEIGHT = 70;
    public final static int FORWARD_FOUR_POINTER_ATTEMPT_WEIGHT = 40;
    public final static int SHOOTING_OVER_PASSING_WEIGHT = 50;

    // Guard - Four Pointer
    public final static int GUARD_FOUR_POINTER_WEIGHT = 60;
    public final static int GUARD_FOUR_BLOCK_WEIGHT = 30;

    // Guard - Two Pointer (Layup or midrange)
    public final static int GUARD_TWO_POINTER_WEIGHT = 60;
    public final static int GUARD_TWO_BLOCK_WEIGHT = 30;

    // Guard - Steal Pass
    public final static int GUARD_PASS_WEIGHT = 60;
    public final static int GUARD_STEAL_WEIGHT = 0;

    // Guard - Rebound
    public final static int GUARD_REBOUND_WEIGHT = 60;
    public final static int GUARD_REBOUND_OPPONENT_WEIGHT = 40;

    // Forward - Four Pointer
    public final static int FORWARD_FOUR_POINTER_WEIGHT = 50;
    public final static int FORWARD_FOUR_BLOCK_WEIGHT = 40;

    // Forward - Two Pointer
    public final static int FORWARD_TWO_POINTER_WEIGHT = 60;
    public final static int FORWARD_TWO_BLOCK_WEIGHT = 40;

    // Forward - Steal (Not functional)
    public final static int FORWARD_PASS_WEIGHT = 70;
    public final static int FORWARD_STEAL_WEIGHT = 20;

    // FORWARD - Rebound
    public final static int FORWARD_REBOUND_WEIGHT = 60;
    public final static int FORWARD_REBOUND_OPPONENT_WEIGHT = 40;

}
