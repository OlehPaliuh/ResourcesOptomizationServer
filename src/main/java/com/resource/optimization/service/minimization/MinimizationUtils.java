package com.resource.optimization.service.minimization;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MinimizationUtils {
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        return Float.parseFloat(bd.toString());
    }

    private MinimizationUtils() {

    }
}
