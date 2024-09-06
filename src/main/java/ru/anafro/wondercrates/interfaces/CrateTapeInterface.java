package ru.anafro.wondercrates.interfaces;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import ru.anafro.wondercrates.Wondercrates;
import ru.anafro.wondercrates.drops.CrateDropTape;
import ru.anafro.wondercrates.utils.arrays.CircularArray;
import ru.anafro.wondercrates.utils.chat.Chat;
import ru.anafro.wondercrates.utils.interfaces.ChestInterface;
import ru.anafro.wondercrates.utils.sounds.Sounds;
import ru.anafro.wondercrates.utils.time.Scheduler;
import ru.anafro.wondercrates.utils.time.TimeSpan;

public class CrateTapeInterface extends ChestInterface {
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
    private final Player player;
    private final int moves;
    private final CrateDropTape tape;
    private int movesRemaining;

    public CrateTapeInterface(Player player) {
        super("Opening the crate...");
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

        if (movesRemaining == 0) {
            playTapeStopEffectAndGivePrize();
        }
    }

    private void playTapeStopEffectAndGivePrize() {
        var prize = tape.at(moves - 1);
        var soundPitch = prize.getRarity().getPercent() / 100.0;

        prize.giveTo(player);
        Chat.send(player, "&aYou won: " + prize.toChatString());
        Sounds.play(player, Sound.UI_TOAST_CHALLENGE_COMPLETE, soundPitch);
        Sounds.play(player, Sound.BLOCK_BEACON_ACTIVATE, 1 - soundPitch);
        Sounds.play(player, Sound.BLOCK_BEACON_DEACTIVATE, soundPitch);
        Scheduler.later(player::closeInventory, TimeSpan.seconds(1));
        stopUpdating();
    }

    private void updateTapeMoveEffect(int step, double speed) {
        for (int x = 0; x < width; x += 1) {
            var drop = tape.at(x + step - width / 2);
            setItem(drop.getMaterial(), drop.getCount(), x, height / 2, "");
            Sounds.play(player, Sound.UI_BUTTON_CLICK, 1 - speed);
        }

        movesRemaining -= 1;
    }

    private void updateDecorativePaneEffect(int tick) {
        for (int x = 0; x < width; x += 1) {
            setItem(DECORATIVE_PANE_MATERIALS.get(x + tick), x, 0);
            setItem(DECORATIVE_PANE_MATERIALS.get(x - tick), x, height - 1);
        }
    }
}
