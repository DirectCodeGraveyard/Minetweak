package org.minetweak.event.item;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.minetweak.entity.Player;

public class ItemPickupEvent extends ItemEvent {
    private Item item;
    private Player player;

    public ItemPickupEvent(ItemStack stack) {
        super();

    }
}
