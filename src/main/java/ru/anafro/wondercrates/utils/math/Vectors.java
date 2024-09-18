package ru.anafro.wondercrates.utils.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import ru.anafro.wondercrates.utils.randoms.Randoms;

public final class Vectors {
    private Vectors() {}

    public static Vector random(double length) {
        return new Vector(Randoms.nextDoubleInRange(length), Randoms.nextDoubleInRange(length), Randoms.nextDoubleInRange(length));
    }

    public static Vector between(Location thisLocation, Location thatLocation) {
        return new Vector(
                thisLocation.getX() - thatLocation.getX(),
                thisLocation.getY() - thatLocation.getY(),
                thisLocation.getZ() - thatLocation.getZ()
        );
    }
}
