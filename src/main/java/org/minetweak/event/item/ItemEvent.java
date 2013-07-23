package org.minetweak.event.item;

import net.minecraft.item.ItemStack;

public class ItemEvent {
    private ItemStack stack;

    public ItemEvent(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack getStack() {
        return stack;
    }
}
