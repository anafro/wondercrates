package ru.anafro.wondercrates.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class CommandExecutor implements org.bukkit.command.CommandExecutor {

    public abstract void performAction(@NotNull CommandSender sender, @NotNull List<String> args);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            performAction(sender, List.of(args));
            return true;
        } catch (CommandExecutionException exception) {
            exception.sendTo(sender);
            return false;
        }
    }
}
