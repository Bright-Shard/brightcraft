package dev.brightshard.brightcraft.lib;

import net.minecraft.util.math.Vec3d;

public class MathTools {
    // new_value = (old_value - old_bottom) / (old_top - old_bottom) * (new_top - new_bottom) + new_bottom
    // https://stackoverflow.com/questions/345187/math-mapping-numbers
    public static double map(double val, double oldMin, double oldMax, double newMin, double newMax) {
        return ((val - oldMin) / (oldMax - oldMin) * (newMax - newMin) + newMin);
    }

    public static Vec3d addVectors(Vec3d one, Vec3d two) {
        return new Vec3d(one.x + two.x, one.y + two.y, one.z + two.z);
    }
}
