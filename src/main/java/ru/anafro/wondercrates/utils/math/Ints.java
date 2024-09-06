package ru.anafro.wondercrates.utils.math;

public final class Ints {
    private Ints() {}

    public static boolean between(int min, int integer, int max) {
        return min <= integer && integer <= max;
    }
}
