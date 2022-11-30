package com.buffsovernexus.engine;

import com.buffsovernexus.GameWeights;
import com.buffsovernexus.engine.helpers.Calculator;
import com.buffsovernexus.entity.Player;

public class EnginePhase {

    public static boolean doesGuardAttemptFourPointer(Player guard) {
        try {
            Calculator calc = new Calculator();
            calc.add(guard.getFourPointer(), GameWeights.GUARD_FOUR_POINTER_ATTEMPT_WEIGHT);
            return calc.result();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean doesForwardAttemptFourPointer(Player forward) {
        try {
            Calculator calc = new Calculator();
            calc.add(forward.getFourPointer(), GameWeights.FORWARD_FOUR_POINTER_ATTEMPT_WEIGHT);
            return calc.result();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean doesGuardAttemptPass(Player guard) {
        try {
            Calculator calc = new Calculator();
            calc.add(guard.getFourPointer(), GameWeights.SHOOTING_OVER_PASSING_WEIGHT);
            calc.add(guard.getTwoPointer(), GameWeights.SHOOTING_OVER_PASSING_WEIGHT);
            return calc.result();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean doesForwardAttemptPass(Player forward) {
        try {
            Calculator calc = new Calculator();
            calc.add(forward.getFourPointer(), GameWeights.SHOOTING_OVER_PASSING_WEIGHT);
            calc.add(forward.getTwoPointer(), GameWeights.SHOOTING_OVER_PASSING_WEIGHT);
            return calc.result();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
