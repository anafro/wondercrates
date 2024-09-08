package ru.anafro.wondercrates.keys;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCrateKeysStorage extends CrateKeysStorage {
    private final Map<String, Integer> playerKeys = new HashMap<>();

    @Override
    public int getKeys(String playerName) {
        return playerKeys.getOrDefault(playerName, 0);
    }

    @Override
    public void setKeys(String playerName, int keys) {
        playerKeys.put(playerName, keys);
    }
}
