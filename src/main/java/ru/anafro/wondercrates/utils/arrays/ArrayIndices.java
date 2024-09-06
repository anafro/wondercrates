package ru.anafro.wondercrates.utils.arrays;

public final class ArrayIndices {
    private ArrayIndices() {}

    @Deprecated(forRemoval = true)
    public static <E> E getLooping(int loopingIndex, E[] elements) {
        return elements[(loopingIndex % elements.length + elements.length) % elements.length];
    }
}
