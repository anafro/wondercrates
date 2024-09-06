package ru.anafro.wondercrates;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.anafro.wondercrates.drops.CrateDrop;
import ru.anafro.wondercrates.drops.CrateDropTape;
import ru.anafro.wondercrates.utils.events.Events;
import ru.anafro.wondercrates.utils.chat.Chat;

import static org.bukkit.Material.*;
import static ru.anafro.wondercrates.drops.CrateDropRarity.*;

@SuppressWarnings("unused")
public class Wondercrates extends JavaPlugin implements Listener {
    private static Wondercrates instance;
    private final CrateDropTape tape = new CrateDropTape(
            new CrateDrop(LEGENDARY, DIAMOND_BLOCK, 64),
            new CrateDrop(LEGENDARY, ENCHANTED_GOLDEN_APPLE, 8),
            new CrateDrop(LEGENDARY, DIAMOND_BLOCK, 4),
            new CrateDrop(EPIC, DIAMOND, 16),
            new CrateDrop(EPIC, NETHER_STAR),
            new CrateDrop(EPIC, GOLD_INGOT, 24),
            new CrateDrop(EPIC, ENCHANTED_GOLDEN_APPLE),
            new CrateDrop(EPIC, IRON_BLOCK, 48),
            new CrateDrop(EPIC, IRON_GOLEM_SPAWN_EGG, 4),
            new CrateDrop(RARE, COOKED_BEEF, 64),
            new CrateDrop(RARE, GOLDEN_APPLE, 2),
            new CrateDrop(RARE, BREAD, 48),
            new CrateDrop(UNCOMMON, DEAD_BUSH, 4),
            new CrateDrop(UNCOMMON, STICK, 16),
            new CrateDrop(COMMON, CAKE),
            new CrateDrop(COMMON, STONE_AXE),
            new CrateDrop(COMMON, OAK_PLANKS, 64),
            new CrateDrop(COMMON, RED_BED),
            new CrateDrop(COMMON, TORCH, 16),
            new CrateDrop(COMMON, LAVA_BUCKET)

    );

    public static Wondercrates getInstance() {
        if (instance == null) {
            throw new UnsupportedOperationException("Wondercrates is not enabled. There is no Wondercrates instance.");
        }

        return instance;
    }

    @Override
    public void onEnable() {
        Wondercrates.instance = this;
        Chat.sendToConsole("&a&lWondercrates plugin is enabled. Enjoy!");
        Events.registerListeners(
                new CrateClickEventListener(),
                new CrateItemPickUpEventListener()
        );
    }

    public CrateDropTape getTape() {
        return tape;
    }
}