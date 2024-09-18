package ru.anafro.wondercrates.interfaces;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.anafro.wondercrates.Wondercrates;
import ru.anafro.wondercrates.drops.CrateDropTape;
import ru.anafro.wondercrates.utils.arrays.CircularArray;
import ru.anafro.wondercrates.utils.chat.Chat;
import ru.anafro.wondercrates.utils.colors.Colors;
import ru.anafro.wondercrates.utils.entities.EntityLocationEffects;
import ru.anafro.wondercrates.utils.interfaces.ChestInterface;
import ru.anafro.wondercrates.utils.items.ItemStacks;
import ru.anafro.wondercrates.utils.particles.Particles;
import ru.anafro.wondercrates.utils.sounds.Sounds;
import ru.anafro.wondercrates.utils.time.Scheduler;
import ru.anafro.wondercrates.utils.time.TimeSpan;

import java.util.concurrent.atomic.AtomicInteger;

import static org.bukkit.Particle.DRIPPING_LAVA;
import static org.bukkit.Particle.EXPLOSION;
import static org.bukkit.Sound.*;
import static ru.anafro.wondercrates.utils.sounds.Sounds.LOWEST_PITCH;

public class CrateSpinInterface extends ChestInterface {
    private static final CircularArray<Material> DECORATIVE_PANE_MATERIALS = new CircularArray<>(
            Material.BLUE_STAINED_GLASS_PANE,
            Material.CYAN_STAINED_GLASS_PANE,
            Material.GREEN_STAINED_GLASS_PANE,
            Material.LIME_STAINED_GLASS_PANE,
            Material.GREEN_STAINED_GLASS_PANE,
            Material.YELLOW_STAINED_GLASS_PANE,
            Material.ORANGE_STAINED_GLASS_PANE,
            Material.RED_STAINED_GLASS_PANE,
            Material.PURPLE_STAINED_GLASS_PANE
    );
    private final Location crateLocation;
    private final Player player;
    private final int moves;
    private final CrateDropTape tape;
    private int movesRemaining;

    public CrateSpinInterface(Location crateLocation, Player player) {
        super("Opening the crate...");
        this.crateLocation = crateLocation;
        this.player = player;
        this.tape = Wondercrates.getInstance().getTape();
        this.moves = tape.nextLoopingDropIndex();
        this.movesRemaining = moves;
    }

    @Override
    public void onUpdate() {
        var step = moves - movesRemaining;
        var progress = (double) movesRemaining / moves;
        var spinSpeed = (int) ((1 - progress) * 10 + 1);

        updateDecorativePaneEffect(tick);

        if (tick % spinSpeed == 0) {
            updateTapeMoveEffect(step, progress);
        }

        if (movesRemaining < 5) {
            updatePumpingEffect();
        }

        if (movesRemaining == 0) {
            playTapeStopEffectAndGivePrize();
        }
    }

    private void updatePumpingEffect() {
        Sounds.play(player, ENTITY_EXPERIENCE_ORB_PICKUP, 1.00);
    }

    private void playTapeStopEffectAndGivePrize() {
        var playerLocation = player.getLocation();
        var prize = tape.at(moves - 1);
        var soundPitch = prize.getRarity().getPercent() / 100.0;
        var itemsLeft = new AtomicInteger(prize.getCount());

        Chat.send(player, "&aYou won: " + prize.toChatString());
        Sounds.play(player, UI_TOAST_CHALLENGE_COMPLETE, soundPitch);
        Sounds.play(player, BLOCK_BEACON_ACTIVATE, 1 - soundPitch);
        Sounds.play(player, BLOCK_BEACON_DEACTIVATE, soundPitch);
        Scheduler.later(player::closeInventory, TimeSpan.SECOND);
        Scheduler.timer((runnable, step) -> {
            var maxSteps = 60;
            var prizeDropLocation = crateLocation.clone().add(0.5, 1, 0.5);

            if (step < maxSteps) {
                Sounds.play(player, BLOCK_ROOTED_DIRT_HIT, 0.00);
                EntityLocationEffects.twirlHead(player, playerLocation, 1);
                updateParticleHurricaneEffect(step, maxSteps);
            }

            if (step == maxSteps) {
                Particles.spawn(prizeDropLocation, EXPLOSION, 2);
                Particles.spawn(prizeDropLocation, DRIPPING_LAVA, 12);
                Sounds.playAt(prizeDropLocation, ENTITY_GENERIC_EXPLODE);
                Sounds.playAt(prizeDropLocation, BLOCK_BEACON_DEACTIVATE, LOWEST_PITCH);
                EntityLocationEffects.repelFrom(player, prizeDropLocation, 1);
            }

            if (step > maxSteps && itemsLeft.getAndDecrement() > 0) {
                var itemStack = new ItemStack(prize.getMaterial());
                ItemStacks.drop(itemStack, prizeDropLocation);
                Sounds.playAt(prizeDropLocation, ENTITY_ITEM_PICKUP);
            }

            if (itemsLeft.get() == 0) {
                runnable.cancel();
            }
        }, TimeSpan.SECOND, TimeSpan.TICK);
        stopUpdating();
    }

    private void updateParticleHurricaneEffect(int step, int maxSteps) {
        var progress = (float) step / maxSteps;
        var count = 5;
        var size = 3 * progress;
        var rays = 4;
        var spinSpeed = 2;
        var height = 25 * (1 - progress);
        var radius = 15 * (1 - progress);
        var color = Colors.gradientPick(0.65, 1, 1, progress, 1, 1, 1);

        for (double angle = 0; angle < 2 * Math.PI; angle += 2 * Math.PI / rays) {
            var alpha = 2 * Math.PI * spinSpeed * progress + angle;
            var location = crateLocation.clone().add(
                    radius * Math.sin(alpha) + 0.5,
                    progress * height,
                    radius * Math.cos(alpha) + 0.5
            );

            Particles.spawnColored(location, count, size, color);
        }
    }

    private void updateTapeMoveEffect(int step, double speed) {
        Sounds.play(player, UI_BUTTON_CLICK, speed);

        for (int x = 0; x < width; x += 1) {
            var drop = tape.at(x + step - HORIZONTAL_CENTER);
            setItem(drop.toItemStack(), x, VERTICAL_CENTER);
        }

        movesRemaining -= 1;
    }

    private void updateDecorativePaneEffect(int tick) {
        for (int x = 0; x < width; x += 1) {
            setItem(DECORATIVE_PANE_MATERIALS.get(x + tick), x, TOP);
            setItem(DECORATIVE_PANE_MATERIALS.get(x - tick), x, BOTTOM);
        }
    }
}
