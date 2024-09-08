package ru.anafro.wondercrates.keys;

import org.bukkit.entity.Player;

public abstract class CrateKeysStorage {

    public abstract int getKeys(String playerName);
    public abstract void setKeys(String playerName, int keys);

    public int getKeys(Player player) {
        return getKeys(player.getName());
    }

    public void setKeys(Player player, int keys) {
        setKeys(player.getName(), keys);
    }

    public void giveKeys(String playerName, int keys) {
        setKeys(playerName, getKeys(playerName) + keys);
    }

    public boolean hasKeys(String playerName) {
        return getKeys(playerName) != 0;
    }

    public void takeOneKey(String playerName) {
        if (!hasKeys(playerName)) {
            throw new UnsupportedOperationException("The player %s does not have crate keys.".formatted(playerName));
        }

        setKeys(playerName, getKeys(playerName) - 1);
    }

    public void giveKeys(Player player, int keys) {
        giveKeys(player.getName(), keys);
    }

    public boolean hasKeys(Player player) {
        return hasKeys(player.getName());
    }

    public void takeOneKey(Player player) {
        takeOneKey(player.getName());
    }
}
