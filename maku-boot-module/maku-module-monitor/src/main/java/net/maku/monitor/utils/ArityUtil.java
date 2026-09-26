package net.maku.monitor.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Exact floating-point arithmetic
 *
 * @author Pure tea
 */
public class ArityUtil {

    /**
     * Default division precision
     */
    private static final int DEF_DIV_SCALE = 10;

    /**
     * This class cannot be instantiated
     */
    private ArityUtil() {

    }

    /**
     * Provides exact addition.
     *
     * @param v1 Augend
     * @param v2 Addend
     * @return Sum of the two parameters
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * Provides exact subtraction.
     *
     * @param v1 Minuend
     * @param v2 Subtrahend
     * @return Difference of the two parameters
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * Provides exact multiplication.
     *
     * @param v1 Multiplicand
     * @param v2 Multiplier
     * @return Product of the two parameters
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * Provides (relatively) exact division; when the division does not terminate, it is exact to
     * 10 digits after the decimal point; digits beyond are rounded.
     *
     * @param v1 Dividend
     * @param v2 Divisor
     * @return Quotient of the two parameters
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * Provides (relatively) exact division. When the division does not terminate, the scale parameter specifies
     * Specify the precision; digits beyond it are rounded.
     *
     * @param v1    Dividend
     * @param v2    Divisor
     * @param scale Indicates how many digits after the decimal point are needed.
     * @return Quotient of the two parameters
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        if (b1.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.doubleValue();
        }
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Provides exact rounding to decimal places.
     *
     * @param v     Number to round
     * @param scale Number of decimal places to keep
     * @return Result after rounding
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = BigDecimal.ONE;
        return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
    }
}
