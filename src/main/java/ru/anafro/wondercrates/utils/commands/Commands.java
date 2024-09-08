package ru.anafro.wondercrates.utils.commands;

import org.bukkit.command.CommandExecutor;
import ru.anafro.wondercrates.Wondercrates;

public final class Commands {
    private Commands() {}

    public static void setExecutor(String label, CommandExecutor executor) {
        var pluginInstance = Wondercrates.getInstance();
        var command = pluginInstance.getCommand(label);

        if (command == null) {
            throw new IllegalArgumentException("The command /%s is not registered in plugin.yml.".formatted(label));
        }

        command.setExecutor(executor);
    }
}
