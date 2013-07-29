package org.minetweak.event.item;

import net.minecraft.item.ItemStack;
import org.minetweak.entity.Player;

public class ItemPickupEvent extends ItemEvent {
    private Player player;

    public ItemPickupEvent(Player player, ItemStack stack) {
        super(new org.minetweak.item.ItemStack(stack));
        this.player = player;
    }

    /**
     * Gets the Player that picked up the item
     *
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }
}
