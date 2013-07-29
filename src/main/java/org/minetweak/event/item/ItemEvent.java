package org.minetweak.event.item;

import org.minetweak.item.ItemStack;

public class ItemEvent {
    private ItemStack stack;

    public ItemEvent(ItemStack stack) {
        this.stack = stack;
    }

    /**
     * Gets the ItemStack associated with the event
     *
     * @return ItemStack
     */
    public ItemStack getStack() {
        return stack;
    }
}
