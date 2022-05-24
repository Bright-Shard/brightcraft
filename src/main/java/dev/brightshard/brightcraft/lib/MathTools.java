package dev.brightshard.brightcraft.lib;

public class MathTools {
    // new_value = (old_value - old_bottom) / (old_top - old_bottom) * (new_top - new_bottom) + new_bottom
    // https://stackoverflow.com/questions/345187/math-mapping-numbers
    public static double map(double val, double oldMin, double oldMax, double newMin, double newMax) {
        return ((val - oldMin) / (oldMax - oldMin) * (newMax - newMin) + newMin);
    }
}
