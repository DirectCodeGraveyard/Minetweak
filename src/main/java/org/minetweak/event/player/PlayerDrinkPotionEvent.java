package org.minetweak.event.player;

import net.minecraft.potion.PotionEffect;
import org.minetweak.entity.Player;

/**
 * Called when a player drinks a potion
 *
 * @see net.minecraft.item.ItemPotion
 */
public class PlayerDrinkPotionEvent extends PlayerEvent {
    private PotionEffect potionEffect;

    public PlayerDrinkPotionEvent(Player player, PotionEffect potionEffect) {
        super(player);
        this.potionEffect = potionEffect;
    }

    public PotionEffect getPotionEffect() {
        return potionEffect;
    }

}
