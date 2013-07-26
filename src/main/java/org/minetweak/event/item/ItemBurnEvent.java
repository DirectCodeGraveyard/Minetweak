package org.minetweak.event.item;

import net.minecraft.item.ItemStack;
import net.minecraft.world.chunk.ChunkCoordinates;

public class ItemBurnEvent {
    private ItemStack itemStack;
    private ChunkCoordinates coordinates;

    public ItemBurnEvent(ItemStack itemStack, ChunkCoordinates coordinates) {
        this.itemStack = itemStack;
        this.coordinates = coordinates;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ChunkCoordinates getCoordinates() {
        return coordinates;
    }
}
