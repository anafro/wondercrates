package ru.anafro.wondercrates.utils.sounds;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Sounds {
    private static final float DEFAULT_VOLUME = 5.0F;
    public static final double LOWEST_PITCH = 0.00;
    public static final double NORMAL_PITCH = 0.50;

    private Sounds() {}

    public static void playAt(Location location, Sound sound, double pitch) {
        var world = location.getWorld();

        if (world == null) {
            throw new UnsupportedOperationException("Can't play sounds at the location with a world unset.");
        }

        world.playSound(location, sound, DEFAULT_VOLUME, convertToBukkitSoundPitch(pitch));
    }

    public static void playAt(Location location, Sound sound) {
        playAt(location, sound, NORMAL_PITCH);
    }

    public static void play(Player player, Sound sound, double pitch) {
        player.playSound(player.getLocation(), sound, DEFAULT_VOLUME, convertToBukkitSoundPitch(pitch));
    }

    public static void play(Player player, Sound sound) {
        play(player, sound, NORMAL_PITCH);
    }

    private static float convertToBukkitSoundPitch(double pitch) {
        return (float) pitch * 2;
    }
}
