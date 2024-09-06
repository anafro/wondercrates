package ru.anafro.wondercrates.utils.inventories;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.anafro.wondercrates.utils.chat.Chat;

public class Inventories {
    public static final int SINGLE_CHEST_INVENTORY_HEIGHT = 3;
    public static final int WIDTH = 9;

    public static Inventory create(String title, int height) {
        return Bukkit.createInventory(null, WIDTH * height, Chat.translateColorCodes(title));
    }

    public static void setItem(Inventory inventory, ItemStack itemStack, int x, int y) {
        inventory.setItem(convertCoordinatesToPosition(x, y), itemStack);
    }

    public static ItemStack getItem(Inventory inventory, int x, int y) {
        return inventory.getItem(convertCoordinatesToPosition(x, y));
    }

    public static int convertCoordinatesToPosition(int x, int y) {
        return y * WIDTH + x;
    }
}
