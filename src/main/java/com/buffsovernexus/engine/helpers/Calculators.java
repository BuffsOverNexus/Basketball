package com.buffsovernexus.engine.helpers;

import com.buffsovernexus.GameWeights;
import com.buffsovernexus.engine.helpers.Calculator;
import com.buffsovernexus.entity.Player;

public class Calculators {
    public static boolean getGuardFourPointer(Player guard, Player opposing) {
        try {
            Calculator calc = new Calculator();
            calc.add(guard.getFourPointer(), GameWeights.GUARD_FOUR_POINTER_WEIGHT);
            calc.add(opposing.getBlock(), GameWeights.GUARD_FOUR_BLOCK_WEIGHT, false);
            return calc.result();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean getForwardFourPointer(Player forward, Player opposing) {
        try {
            Calculator calc = new Calculator();
            calc.add(forward.getFourPointer(), GameWeights.FORWARD_FOUR_POINTER_WEIGHT);
            calc.add(opposing.getBlock(), GameWeights.FORWARD_FOUR_BLOCK_WEIGHT, false);
            return calc.result();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean getGuardTwoPointer(Player guard, Player opposing) {
        try {
            Calculator calc = new Calculator();
            calc.add(guard.getFourPointer(), GameWeights.GUARD_TWO_POINTER_WEIGHT);
            calc.add(opposing.getBlock(), GameWeights.GUARD_TWO_BLOCK_WEIGHT, false);
            return calc.result();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean getForwardTwoPointer(Player forward, Player opposing) {
        try {
            Calculator calc = new Calculator();
            calc.add(forward.getTwoPointer(), GameWeights.FORWARD_TWO_POINTER_WEIGHT);
            calc.add(opposing.getBlock(), GameWeights.FORWARD_TWO_BLOCK_WEIGHT, false);
            return calc.result();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean getForwardRebound(Player forward, Player opposing) {
        try {
            Calculator calc = new Calculator();
            calc.add(forward.getRebound(), GameWeights.FORWARD_REBOUND_WEIGHT);
            calc.add(opposing.getBlock(), GameWeights.FORWARD_REBOUND_OPPONENT_WEIGHT, false);
            return calc.result();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean getGuardRebound(Player guard, Player opposing) {
        try {
            Calculator calc = new Calculator();
            calc.add(guard.getRebound(), GameWeights.GUARD_REBOUND_WEIGHT);
            calc.add(opposing.getBlock(), GameWeights.GUARD_REBOUND_OPPONENT_WEIGHT, false);
            return calc.result();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean getGuardPass(Player guard, Player opposing) {
        try {
            Calculator calc = new Calculator();
            calc.add(GameWeights.ABSOLUTE, GameWeights.GUARD_PASS_WEIGHT);
            calc.add(opposing.getBlock(), GameWeights.GUARD_STEAL_WEIGHT, false);
            return calc.result();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean getForwardPass(Player forward, Player opposing) {
        try {
            Calculator calc = new Calculator();
            calc.add(GameWeights.ABSOLUTE, GameWeights.FORWARD_PASS_WEIGHT);
            calc.add(opposing.getBlock(), GameWeights.FORWARD_STEAL_WEIGHT, false);
            return calc.result();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
