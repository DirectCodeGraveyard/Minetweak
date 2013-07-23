package org.minetweak.event.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.minetweak.entity.Player;

public class ItemPickupEvent extends ItemEvent {
    private Item item;
    private Player player;

    public ItemPickupEvent(Player player, ItemStack stack) {
        super(null);
    }
}
