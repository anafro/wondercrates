package ru.anafro.wondercrates.utils.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.anafro.wondercrates.utils.chat.Chat;

public class CommandExecutionException extends RuntimeException {
    public CommandExecutionException(@NotNull String message) {
        super(message);
    }

    public void sendTo(@NotNull CommandSender sender) {
        Chat.send(sender, "&c" + this.getMessage());
    }
}
