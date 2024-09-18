package ru.anafro.wondercrates.utils.particles;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

public final class Particles {
    private Particles() {}

    public static void spawn(Location location, Particle particle, int count) {
        var world = location.getWorld();

        if (world == null) {
            throw new UnsupportedOperationException("Can't spawn particles at the location with a world unset.");
        }

        world.spawnParticle(particle, location, count);
    }

    public static void spawnColored(Location location, int count, float size, Color color) {
        var dustOptions = new Particle.DustOptions(color, size);
        var world = location.getWorld();

        if (world == null) {
            throw new UnsupportedOperationException("Can't spawn particles at the location with a world unset.");
        }

        world.spawnParticle(Particle.DUST, location, count, dustOptions);
    }
}
