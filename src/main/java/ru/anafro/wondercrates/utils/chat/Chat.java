package ru.anafro.wondercrates.utils.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class Chat {
    private static final char ALTERNATE_COLOR_CHAR = '&';

    private Chat() {}

    public static void send(Player player, String message) {
        player.sendMessage(translateColorCodes(message));
    }

    public static void sendToConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(translateColorCodes(message));
    }

    public static String translateColorCodes(String message) {
        return ChatColor.translateAlternateColorCodes(ALTERNATE_COLOR_CHAR, message);
    }
}
