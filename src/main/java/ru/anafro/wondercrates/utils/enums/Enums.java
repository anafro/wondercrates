package ru.anafro.wondercrates.utils.enums;

import ru.anafro.wondercrates.utils.strings.Strings;

public final class Enums {
    private Enums() {}

    public static <E extends Enum<E>> String getPrettyName(E value) {
        return Strings.capitalize(value.name().replace('_', ' '));
    }
}
