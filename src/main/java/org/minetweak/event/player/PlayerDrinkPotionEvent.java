package org.minetweak.event.player;

import net.minecraft.src.PotionEffect;
import org.minetweak.entity.Player;

/**
 * Called when a player drinks a potion
 * @see net.minecraft.src.ItemPotion
 */
public class PlayerDrinkPotionEvent {

    private Player player;
    private PotionEffect potionEffect;

    public PlayerDrinkPotionEvent(Player player, PotionEffect potionEffect) {
        this.player = player;
        this.potionEffect = potionEffect;
    }

    public Player getPlayer() {
        return player;
    }

    public PotionEffect getPotionEffect() {
        return potionEffect;
    }

}
