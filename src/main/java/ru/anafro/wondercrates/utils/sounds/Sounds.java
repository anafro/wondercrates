package ru.anafro.wondercrates.utils.sounds;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Sounds {
    private Sounds() {}

    public static void play(Player player, Sound sound, double pitch) {
        player.playSound(player.getLocation(), sound, 5, (float) pitch * 2);
    }

    public static void play(Player player, Sound sound) {
        play(player, sound, 0.50);
    }
}
