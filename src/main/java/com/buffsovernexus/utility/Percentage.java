package com.buffsovernexus.utility;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Percentage {
    public static double calculatePercentage(int wins, int losses) {
        return (double) wins / (wins + losses);
    }

    public static String toPretty(double input) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(input);
    }
}
