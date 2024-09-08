package ru.anafro.wondercrates.utils.time;

import org.bukkit.scheduler.BukkitRunnable;
import ru.anafro.wondercrates.Wondercrates;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class Scheduler {
    private int step;
    private final BukkitRunnable bukkitRunnable;

    private Scheduler(BiConsumer<BukkitRunnable, Integer> action, TimeSpan delay, TimeSpan period) {
        var pluginInstance = Wondercrates.getInstance();
        this.step = 0;
        this.bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                action.accept(this, step);
                step += 1;
            }
        };

        bukkitRunnable.runTaskTimer(pluginInstance, delay.getTicks(), period.getTicks());
    }

    public static Scheduler timer(BiConsumer<BukkitRunnable, Integer> action, TimeSpan delay, TimeSpan period) {
        return new Scheduler(action, delay, period);
    }

    public static Scheduler timer(BiConsumer<BukkitRunnable, Integer> action, TimeSpan period) {
        return timer(action, TimeSpan.INSTANT, period);
    }

    public static Scheduler timer(Consumer<BukkitRunnable> action, TimeSpan delay, TimeSpan period) {
        return new Scheduler((runnable, step) -> action.accept(runnable), delay, period);
    }

    public static Scheduler timer(Consumer<BukkitRunnable> action, TimeSpan period) {
        return timer((runnable, step) -> action.accept(runnable), TimeSpan.INSTANT, period);
    }

    public static void later(Runnable action, TimeSpan delay) {
        var pluginInstance = Wondercrates.getInstance();

        new BukkitRunnable() {
            @Override
            public void run() {
                action.run();
            }
        }.runTaskLater(pluginInstance, delay.getTicks());
    }

    public void cancel() {
        bukkitRunnable.cancel();
    }
}
