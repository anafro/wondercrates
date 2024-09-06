package ru.anafro.wondercrates.drops;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.anafro.wondercrates.utils.chat.Chat;
import ru.anafro.wondercrates.utils.enums.Enums;

import java.util.Objects;

public final class CrateDrop {
    private final CrateDropRarity rarity;
    private final Material material;
    private final int count;

    public CrateDrop(CrateDropRarity rarity, Material material, int count) {
        this.rarity = rarity;
        this.material = material;
        this.count = count;
    }

    public CrateDrop(CrateDropRarity rarity, Material material) {
        this(rarity, material, 1);
    }

    public String toChatString() {
        return Chat.translateColorCodes(rarity.toChatString() + "&7: &f" + Enums.getPrettyName(material) + "&7 x " + count);
    }

    public Material getMaterial() {
        return material;
    }

    public int getCount() {
        return count;
    }

    public CrateDropRarity getRarity() {
        return rarity;
    }

    public void giveTo(Player player) {
        var location = player.getLocation();
        var world = player.getWorld();
        var itemStack = new ItemStack(material, count);

        world.dropItem(location, itemStack);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CrateDrop) obj;
        return Objects.equals(this.rarity, that.rarity) &&
                Objects.equals(this.material, that.material) &&
                this.count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rarity, material, count);
    }

    @Override
    public String toString() {
        return "CrateDrop[" +
                "rarity=" + rarity + ", " +
                "material=" + material + ", " +
                "count=" + count + ']';
    }
}
