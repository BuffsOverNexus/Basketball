package com.buffsovernexus.engine.helpers;

import lombok.Builder;
import lombok.Data;

import java.util.Random;

@Data
@Builder
public class Check {

    private int stat;

    /***
     * Receive the result from probability check
     * Note: Calculation is from 0 to STAT_GIVEN. If the number is higher, it fails.
     * @return result of the probability check, either true or false.
     */
    public boolean result() {
        Random random = new Random();
        int result = random.nextInt(100);
        return result < stat;
    }
}
