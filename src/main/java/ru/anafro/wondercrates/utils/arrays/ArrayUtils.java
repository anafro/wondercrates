package ru.anafro.wondercrates.utils.arrays;

import java.lang.reflect.Array;
import java.util.Arrays;

public final class ArrayUtils {
    private ArrayUtils() {}

    public static <T> T[] join(T[]... arrays) {
        var arrayType = arrays.getClass().getComponentType().getComponentType();

        return Arrays.stream(arrays)
                .flatMap(i -> Arrays.stream(i))
                .toArray(size -> (T[]) Array.newInstance(arrayType, size));
    }

    public static <T> T[] array(T... elements) {
        return elements;
    }
}
