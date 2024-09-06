package ru.anafro.wondercrates.drops;

import org.bukkit.ChatColor;
import ru.anafro.wondercrates.utils.math.Ints;

public class CrateDropRarity {
    public static final CrateDropRarity LEGENDARY = new CrateDropRarity(10);
    public static final CrateDropRarity EPIC = new CrateDropRarity(30);
    public static final CrateDropRarity RARE = new CrateDropRarity(50);
    public static final CrateDropRarity UNCOMMON = new CrateDropRarity(70);
    public static final CrateDropRarity COMMON = new CrateDropRarity(100);
    private final int rarity;
    private final ChatColor chatColor;
    private final String name;

    public CrateDropRarity(int rarityPercent) {
        this.rarity = rarityPercent;

        if (Ints.between(0, rarityPercent, 20)) {
            this.chatColor = ChatColor.GOLD;
            this.name = "Legendary";
        } else if (rarityPercent <= 40) {
            this.chatColor = ChatColor.DARK_PURPLE;
            this.name = "Epic";
        } else if (rarityPercent <= 60) {
            this.chatColor = ChatColor.AQUA;
            this.name = "Rare";
        } else if (rarityPercent <= 80) {
            this.chatColor = ChatColor.BLUE;
            this.name = "Uncommon";
        } else if (rarityPercent <= 100) {
            this.chatColor = ChatColor.GRAY;
            this.name = "Common";
        } else {
            throw new IllegalArgumentException("The rarityPercent must be between 0 and 100, not %o.".formatted(rarityPercent));
        }
    }

    public int getPercent() {
        return rarity;
    }

    public String toChatString() {
        return chatColor + name;
    }
}
