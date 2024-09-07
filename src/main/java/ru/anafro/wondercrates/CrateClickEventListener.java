package ru.anafro.wondercrates;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import ru.anafro.wondercrates.interfaces.OpenCrateInterface;
import ru.anafro.wondercrates.utils.events.Events;
import ru.anafro.wondercrates.utils.items.ItemStacks;
import ru.anafro.wondercrates.utils.chat.Chat;
import ru.anafro.wondercrates.utils.math.Vectors;
import ru.anafro.wondercrates.utils.sounds.Sounds;

import java.util.UUID;

import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class CrateClickEventListener implements Listener {
    private static final CrateKeysStorage KEY_STORAGE = new CrateKeysStorage(10);
    private static final Sound CRATE_CLICK_SOUND = Sound.ENTITY_PLAYER_LEVELUP;
    private static final Material CRATE_MATERIAL = Material.ENDER_CHEST;
    public static final String CRATE_DROP_ITEM_NAME_PREFIX = "Crate-Item@";
    private static final Material CRATE_DROP_ITEM_MATERIAL = Material.MELON;
    private static final int CRATE_DROP_ITEM_VELOCITY = 1;
    private static final int CRATE_DROP_ITEM_COUNT = 8;

    @EventHandler
    public void onChestOpen(PlayerInteractEvent event) {
        var player = event.getPlayer();
        var world = player.getWorld();
        var block = event.getClickedBlock();

        if (!isCrate(block) || event.getAction() != RIGHT_CLICK_BLOCK) {
            return;
        }

        var openCrateInterface = new OpenCrateInterface(KEY_STORAGE, player);

        splashEffectItems(world, block);
        openCrateInterface.showTo(player);

        Chat.send(player, "You have &a" + KEY_STORAGE.getKeys(player) + "&r keys.");
        Sounds.play(player, CRATE_CLICK_SOUND, 1.00);
        Events.cancel(event);
    }

    private boolean isCrate(Block block) {
        return block != null && block.getType() == CRATE_MATERIAL;
    }

    private void splashEffectItems(World world, Block crate) {
        var location = crate.getLocation();

        for (int i = 0; i < CRATE_DROP_ITEM_COUNT; i += 1) {
            var itemStack = ItemStacks.create(CRATE_DROP_ITEM_MATERIAL, CRATE_DROP_ITEM_NAME_PREFIX + UUID.randomUUID());
            var item = world.dropItem(location.add(0, 2, 0), itemStack);

            item.setVelocity(Vectors.random(CRATE_DROP_ITEM_VELOCITY));
        }
    }
}
