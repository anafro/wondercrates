package ru.anafro.wondercrates.utils.inventories;

import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class ChestButtonEventListener {
    private final int x;
    private final int y;
    private final Consumer<InventoryClickEvent> clickAction;

    public ChestButtonEventListener(int x, int y, Consumer<InventoryClickEvent> clickAction) {
        this.x = x;
        this.y = y;
        this.clickAction = clickAction;
    }

    public int getItemPosition() {
        return Inventories.convertCoordinatesToPosition(x, y);
    }

    public boolean isTriggeredBy(InventoryClickEvent event) {
        return event.getSlot() == getItemPosition();
    }

    public void trigger(InventoryClickEvent event) {
        clickAction.accept(event);
    }
}
