package ru.anafro.wondercrates.interfaces;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import ru.anafro.wondercrates.CrateKeysStorage;
import ru.anafro.wondercrates.Wondercrates;
import ru.anafro.wondercrates.utils.arrays.CircularArray;
import ru.anafro.wondercrates.utils.chat.Chat;
import ru.anafro.wondercrates.utils.interfaces.ChestInterface;
import ru.anafro.wondercrates.utils.sounds.Sounds;
import ru.anafro.wondercrates.utils.time.TimeSpan;

import static ru.anafro.wondercrates.utils.arrays.ArrayUtils.array;
import static ru.anafro.wondercrates.utils.arrays.ArrayUtils.join;

public class OpenCrateInterface extends ChestInterface {
    private static final Material FILLER_MATERIAL = Material.GRAY_STAINED_GLASS_PANE;
    private static final CircularArray<Material> DECORATIVE_PANE_MATERIALS = new CircularArray<>(
            Material.RED_STAINED_GLASS_PANE,
            Material.ORANGE_STAINED_GLASS_PANE,
            Material.YELLOW_STAINED_GLASS_PANE,
            Material.WHITE_STAINED_GLASS_PANE
    );

    public OpenCrateInterface(CrateKeysStorage keysStorage, Player player) {
        super("Open a crate", 5);

        setButton(
                "&a&lOpen a crate",
                join(
                        array(
                            "Test your luck!",
                            "You have &a" + keysStorage.getKeys(player) + "&f keys",
                            "",
                            "Here, you may get:"
                        ),

                        Wondercrates.getInstance().getTape().getLore(),

                        array(
                                "",
                                "&8Click here to open a crate"
                        )
                ),

                Material.TRIPWIRE_HOOK,
                width / 2,
                height / 2,

                event -> {
                    if (keysStorage.hasKeys(player)) {
                        keysStorage.takeOneKey(player);
                        new CrateTapeInterface(player).showTo(player);
                    } else {
                        Sounds.play(player, Sound.ENTITY_VILLAGER_NO);
                        Chat.send(player, "&cYou don't have crate keys. Purchase them at &ehttps://wondercrates.anafro.ru/");
                        player.closeInventory();
                    }

                    unregisterEvents();
                }
        );
    }

    @Override
    public void onUpdate() {
        every(TimeSpan.ticks(8), this::updateDecorativePaneEffect);
    }

    private void updateDecorativePaneEffect(int step) {
        for (int x = 0; x < width; x += 1) {
            setItem(DECORATIVE_PANE_MATERIALS.get(x + step), x, 0, " ");
            setItem(DECORATIVE_PANE_MATERIALS.get(x - step), x, height - 1, " ");

            for (int y = 0; y < height; y += 1) {
                if (!hasItemAt(x, y)) {
                    setItem(FILLER_MATERIAL, x, y, " ");
                }
            }
        }
    }
}
