package ru.anafro.wondercrates.utils.math;

import org.bukkit.util.Vector;
import ru.anafro.wondercrates.utils.events.Randoms;

public final class Vectors {
    private Vectors() {}

    public static Vector random(double length) {
        return new Vector(Randoms.nextDoubleInRange(length), Randoms.nextDoubleInRange(length), Randoms.nextDoubleInRange(length));
    }
}
