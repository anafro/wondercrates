package ru.anafro.wondercrates.utils.math;

import java.util.function.Supplier;

public final class Ints {
    private Ints() {}

    public static boolean between(int min, int integer, int max) {
        return min <= integer && integer <= max;
    }

    public static int clamp(int min, int integer, int max) {
        return Math.max(min, Math.min(integer, max));
    }

    public static int parse(String string, Supplier<? extends RuntimeException> throwableSupplier) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException exception) {
            throw throwableSupplier.get();
        }
    }
}
