package ru.anafro.wondercrates;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.anafro.wondercrates.utils.time.TimeSpan;

public class CrateItemPickUpEventListener implements Listener {
    private static final Sound SOUND = Sound.ENTITY_PLAYER_BURP;
    private static final float SOUND_VOLUME = 2.0F;
    private static final float SOUND_PITCH = 2.0F;
    private static final PotionEffectType EFFECT  = PotionEffectType.SPEED;
    private static final TimeSpan EFFECT_DURATION = TimeSpan.seconds(2);
    private static final int EFFECT_AMPLIFIER = 2;

    @EventHandler
    public void onItemPickUp(EntityPickupItemEvent event) {
        var item = event.getItem();
        var location = item.getLocation();
        var world = location.getWorld();
        var entity = event.getEntity();

        if (item.getItemStack().getItemMeta().getDisplayName().startsWith(CrateClickEventListener.CRATE_DROP_ITEM_NAME_PREFIX)) {
            event.setCancelled(true);
            item.remove();
            world.playSound(location, SOUND, SOUND_VOLUME, SOUND_PITCH);
            entity.addPotionEffect(new PotionEffect(EFFECT, EFFECT_DURATION.getTicks(), EFFECT_AMPLIFIER));
        }
    }
}
