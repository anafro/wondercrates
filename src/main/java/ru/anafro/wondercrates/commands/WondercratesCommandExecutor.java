package ru.anafro.wondercrates.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.anafro.wondercrates.keys.CrateKeysStorage;
import ru.anafro.wondercrates.utils.chat.Chat;
import ru.anafro.wondercrates.utils.commands.CommandExecutionException;
import ru.anafro.wondercrates.utils.commands.CommandExecutor;
import ru.anafro.wondercrates.utils.math.Ints;

import java.util.List;

public class WondercratesCommandExecutor extends CommandExecutor {
    private final CrateKeysStorage keysStorage;

    public WondercratesCommandExecutor(CrateKeysStorage keysStorage) {
        this.keysStorage = keysStorage;
    }

    @Override
    public void performAction(@NotNull CommandSender sender, @NotNull List<String> args) {
        if (args.isEmpty()) {
            sendCommandHelpListTo(sender);
            return;
        }

        var command = args.getFirst().toLowerCase();

        switch (command) {
            case "give" -> performGiveCommand(sender, args);
            case "set" -> performSetCommand(sender, args);
            case "get" -> performGetCommand(sender, args);
            default -> sendCommandHelpListTo(sender);
        }
    }

    private void performGiveCommand(@NotNull CommandSender sender, @NotNull List<String> args) {
        if (args.size() != 3) {
            throw new CommandExecutionException("The correct syntax: /wondercrates give <player> <number>.");
        }

        var playerName = args.get(1);
        var keys = Ints.parse(args.get(2), () -> new CommandExecutionException("Number of keys must be an integer"));

        keysStorage.giveKeys(playerName, keys);
        Chat.send(sender, "&a%d key(s) were given to %s.".formatted(keys, playerName));
    }

    private void performSetCommand(@NotNull CommandSender sender, @NotNull List<String> args) {
        if (args.size() != 3) {
            throw new CommandExecutionException("The correct syntax: /wondercrates set <player> <number>.");
        }

        var playerName = args.get(1);
        var keys = Ints.parse(args.get(2), () -> new CommandExecutionException("Number of keys must be an integer"));

        keysStorage.setKeys(playerName, keys);
        Chat.send(sender, "&a%s now has %d key(s).".formatted(playerName, keys));
    }

    private void performGetCommand(@NotNull CommandSender sender, @NotNull List<String> args) {
        if (args.size() != 2) {
            throw new CommandExecutionException("The correct syntax: /wondercrates get <player>.");
        }

        var playerName = args.get(1);
        var keys = keysStorage.getKeys(playerName);

        Chat.send(sender, "&a%s has %d key(s).".formatted(playerName, keys));
    }

    private void sendCommandHelpListTo(@NotNull CommandSender sender) {
        Chat.send(
                sender,

                "&aWondercrates",
                "=========================",
                "&a/wondercrates give <player> <number> &f- gives some crate keys to a player",
                "&a/wondercrates set <player> <number> &f- set crate keys for a player",
                "&a/wondercrates get <player> &f- show crate keys of a player",
                "========================="
        );
    }
}
