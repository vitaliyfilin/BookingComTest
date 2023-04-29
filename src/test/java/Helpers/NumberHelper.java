package Helpers;

import java.util.List;

public class NumberHelper {
    public static boolean isWithinRange(List<Long> values, long min, long max, long nightsQuantity) {
        for (long number : values) {
            if (number/nightsQuantity <= min || number/nightsQuantity >= max) {
                return false;
            }
        }
        return true;
    }
}
