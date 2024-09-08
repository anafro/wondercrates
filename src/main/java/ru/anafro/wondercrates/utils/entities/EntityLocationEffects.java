package ru.anafro.wondercrates.utils.entities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import ru.anafro.wondercrates.utils.events.Randoms;
import ru.anafro.wondercrates.utils.math.Vectors;

public final class EntityLocationEffects {
    private EntityLocationEffects() {}

    public static void twirlHead(Entity entity, Location originalLocation, double degrees) {
        var pitch = originalLocation.getPitch() + Randoms.nextDoubleInRange(degrees);
        var yaw = originalLocation.getYaw() + Randoms.nextDoubleInRange(degrees);
        var twirledLocation = originalLocation.clone();

        twirledLocation.setPitch((float) pitch);
        twirledLocation.setYaw((float) yaw);

        entity.teleport(twirledLocation);
    }

    public static void twirlHead(Entity entity, Location originalLocation) {
        twirlHead(entity, originalLocation, 1);
    }

    public static void repelFrom(Entity entity, Location repelLocation, float repelForce) {
        var entityLocation = entity.getLocation();
        var repelVelocity = Vectors.between(entityLocation, repelLocation).normalize().multiply(repelForce);

        entity.setVelocity(repelVelocity);
    }
}
