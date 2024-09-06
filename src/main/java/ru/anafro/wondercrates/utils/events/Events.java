package ru.anafro.wondercrates.utils.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Listener;
import ru.anafro.wondercrates.Wondercrates;

public final class Events {
    private Events() {}

    public static <E extends Cancellable> void cancel(E event) {
        event.setCancelled(true);
    }

    public static void registerListeners(Listener... listeners) {
        for (var listener : listeners) {
            registerListener(listener);
        }
    }

    public static void registerListener(Listener listener) {
        var pluginInstance = Wondercrates.getInstance();
        var pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(listener, pluginInstance);
    }
}
