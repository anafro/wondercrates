package ru.anafro.wondercrates;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CrateKeysStorage {
    private final Map<UUID, Integer> playerKeys = new HashMap<>();
    private final int initialKeysCount;

    public CrateKeysStorage(int initialKeysCount) {
        this.initialKeysCount = initialKeysCount;
    }

    public int getKeys(Player player) {
        return playerKeys.getOrDefault(player.getUniqueId(), initialKeysCount);
    }

    public void giveKeys(Player player, int keys) {
        setKeys(player, getKeys(player) + keys);
    }

    public boolean hasKeys(Player player) {
        return getKeys(player) != 0;
    }

    public void takeOneKey(Player player) {
        if (!hasKeys(player)) {
            throw new UnsupportedOperationException("The player %s does not have crate keys.".formatted(player.getName()));
        }

        setKeys(player, getKeys(player) - 1);
    }

    private void setKeys(Player player, int keys) {
        playerKeys.put(player.getUniqueId(), keys);
    }
}
