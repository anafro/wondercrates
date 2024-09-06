package ru.anafro.wondercrates.utils.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.anafro.wondercrates.utils.chat.Chat;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public final class ItemStacks {
    private ItemStacks() {}

    public static ItemStack create(Material material, int count, String name, String... lore) {
        var itemStack = new ItemStack(material, count);
        setName(itemStack, name);
        setLore(itemStack, lore);

        return itemStack;
    }

    public static ItemStack create(Material material, String name, String... lore) {
        return create(material, 1, name, lore);
    }

    public static void setName(ItemStack itemStack, String name) {
        editMeta(itemStack, itemMeta -> itemMeta.setDisplayName(Chat.translateColorCodes(name)));
    }

    public static void setLore(ItemStack itemStack, String... lore) {
        editMeta(itemStack, itemMeta -> itemMeta.setLore(translateLoreColorCodes(lore)));
    }

    private static ItemMeta getMeta(ItemStack itemStack) {
        var itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            throw new UnsupportedOperationException("The item meta is null.");
        }

        return itemMeta;
    }

    private static List<String> translateLoreColorCodes(String... lore) {
        return Arrays.asList(lore)
                .stream()
                .map(line -> "&f" + line)
                .map(Chat::translateColorCodes)
                .toList();
    }

    private static void editMeta(ItemStack itemStack, Consumer<ItemMeta> changeAction) {
        var itemMeta = ItemStacks.getMeta(itemStack);
        changeAction.accept(itemMeta);
        itemStack.setItemMeta(itemMeta);
    }
}
