package ru.anafro.wondercrates.utils.interfaces;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.anafro.wondercrates.utils.events.Events;
import ru.anafro.wondercrates.utils.inventories.ChestButtonEventListener;
import ru.anafro.wondercrates.utils.inventories.Inventories;
import ru.anafro.wondercrates.utils.items.ItemStacks;
import ru.anafro.wondercrates.utils.time.Scheduler;
import ru.anafro.wondercrates.utils.time.TimeSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class ChestInterface implements Listener, InventoryHolder {
    private static final TimeSpan UPDATE_PERIOD = TimeSpan.TICK;
    protected final int LEFT;
    protected final int RIGHT;
    protected final int TOP;
    protected final int BOTTOM;
    protected final int HORIZONTAL_CENTER;
    protected final int VERTICAL_CENTER;
    private final UUID uuid;
    private final List<ChestButtonEventListener> listeners;
    private final Inventory inventory;
    private final Scheduler updateScheduler;
    protected int tick = 0;
    protected final int height;
    protected final int width = Inventories.WIDTH;

    public ChestInterface(String title, int height) {
        this.uuid = UUID.randomUUID();
        this.height = height;
        this.inventory = Inventories.create(this, title, height);
        this.listeners = new ArrayList<>();
        this.updateScheduler = Scheduler.timer(runnable -> {
            onUpdate();
            tick += UPDATE_PERIOD.getTicks();
        }, UPDATE_PERIOD);
        Events.registerListener(this);

        this.LEFT = 0;
        this.RIGHT = width - 1;
        this.TOP = 0;
        this.BOTTOM = height - 1;
        this.HORIZONTAL_CENTER = width / 2;
        this.VERTICAL_CENTER = height / 2;
    }

    public ChestInterface(String title) {
        this(title, Inventories.SINGLE_CHEST_INVENTORY_HEIGHT);
    }

    public void showTo(Player player) {
        player.openInventory(inventory);
    }

    public void setButton(String title, String[] lore, Material material, int x, int y, Consumer<InventoryClickEvent> clickAction) {
        setItem(material, x, y, title, lore);
        addEventListener(x, y, clickAction);
    }

    public void setItem(ItemStack itemStack, int x, int y) {
        Inventories.setItem(inventory, itemStack, x, y);
    }

    public void setItem(Material material, int count, int x, int y, String title, String... lore) {
        setItem(ItemStacks.create(material, count, title, lore), x, y);
    }

    public void setItem(Material material, int x, int y, String title, String... lore) {
        setItem(material, 1, x, y, title, lore);
    }

    public void setItem(Material material, int x, int y) {
        setItem(material, 1, x, y, " ");
    }

    public void setItemIfEmpty(Material material, int x, int y) {
        if (!hasItemAt(x, y)) {
            setItem(material, x, y);
        }
    }

    public void addEventListener(int x, int y, Consumer<InventoryClickEvent> clickAction) {
        listeners.add(new ChestButtonEventListener(x, y, clickAction));
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if (!holds(event.getClickedInventory())) {
            return;
        }

        Events.cancel(event);

        for (var listener : listeners) {
            if (listener.isTriggeredBy(event)) {
                listener.trigger(event);
            }
        }
    }

    public abstract void onUpdate();

    public boolean hasItemAt(int x, int y) {
        return Inventories.getItem(inventory, x, y) != null;
    }

    protected void every(TimeSpan period, Consumer<Integer> action) {
        if (tick % period.getTicks() == 0) {
            int step = tick / period.getTicks();
            action.accept(step);
        }
    }

    protected void stopUpdating() {
        updateScheduler.cancel();
    }

    protected void unregisterEvents() {
        HandlerList.unregisterAll(this);
    }

    private boolean holds(Inventory inventory) {
        if (inventory == null) {
            return false;
        }

        var holder = inventory.getHolder();

        if (holder == null) {
            return false;
        }

        return holder.equals(this);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChestInterface that = (ChestInterface) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }
}
