package com.buffsovernexus.engine.helpers;

import com.buffsovernexus.engine.exceptions.InvalidCalculatorWeightException;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/***
 * Handles decisions for the engine
 */
@Data
public class Calculator {

    private Map<Check, WeightedReward> conditions = new HashMap<>();

    /**
     * Add a condition (check), how much it is worth (0 to 100), and what the expected result is.
     * @param check - The value to check (e.g. the player's current four pointer)
     * @param weight - The maximum weight to accept (0 to 100, e.g. the maximum four pointer)
     * @param reward - What the expected result is (if you intend to miss, use false, otherwise, if you want to score, use true)
     * @throws InvalidCalculatorWeightException
     */
    public void add(int check, int weight, boolean reward) throws InvalidCalculatorWeightException {
        add(Check.builder().stat(check).build(), weight, reward);
    }
    public void add(Check check, int weight, boolean reward) throws InvalidCalculatorWeightException {
        add(check, new WeightedReward(weight, reward));
    }

    public void add(Check check, WeightedReward reward) throws InvalidCalculatorWeightException {
        if (!isValid()) {
            conditions.put(check, reward);
        } else {
            throw new InvalidCalculatorWeightException("You must not exceed 100% in weight.");
        }
    }

    public boolean isValid() {
        int totalWeight = 0;
        for (WeightedReward reward : conditions.values()) {
            totalWeight += reward.getWeight();
        }
        return totalWeight >= 100;
    }

    public boolean result() {
        try {
            Random random = new Random();
            int result = random.nextInt(100);
            if (isValid()) {
                System.out.println("Resulting Integer: " + result);
                int acceptedWeight = 0;
                for (Entry<Check, WeightedReward> condition : conditions.entrySet()) {
                    boolean conditionResult = condition.getKey().result();
                    if (conditionResult && condition.getValue().getReward()) {
                        acceptedWeight += condition.getValue().getWeight();
                    }
                }
                return result <= acceptedWeight;
            } else {
                // Add a filler weight based on missing weight.
                Check check = Check.builder().stat(50).build();
                add(check, getWeight(), true);
                // Automatically fill in the rest with a weight.
                int acceptedWeight = 0;
                for (Entry<Check, WeightedReward> condition : conditions.entrySet()) {
                    boolean conditionResult = condition.getKey().result();
                    // Determine if the condition was met AND the reward matches with it.
                    if (conditionResult && condition.getValue().getReward()) {
                        acceptedWeight += condition.getValue().getWeight();
                    }
                }
                return result <= acceptedWeight;
            }
        } catch (InvalidCalculatorWeightException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    private int getWeight() {
        int totalReward = 0;
        for (WeightedReward reward : conditions.values()) {
            totalReward += reward.getWeight();
        }
        return totalReward;
    }
}
