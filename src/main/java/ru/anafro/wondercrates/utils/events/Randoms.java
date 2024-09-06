package ru.anafro.wondercrates.utils.events;

import java.util.Random;

public final class Randoms {
    private static final Random random = new Random();

    private Randoms() {}

    public static double nextDoubleInRange(double range) {
        return (random.nextDouble() * 2 - 1) * range;
    }

    public static double nextDoubleAround(double number, double range) {
        return number + nextDoubleInRange(range);
    }

    public static int nextInt(int max) {
        return random.nextInt(max);
    }

    public static int nextIntAround(int integer, int range) {
        return random.nextInt(integer - range, integer + range + 1);
    }
}
