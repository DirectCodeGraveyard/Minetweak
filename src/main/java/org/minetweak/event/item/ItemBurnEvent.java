package org.minetweak.event.item;

import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.Item;

public class ItemBurnEvent {
    private Item item;
    private ChunkCoordinates coordinates;

    public ItemBurnEvent(Item item, ChunkCoordinates coordinates) {
        this.item = item;
        this.coordinates = coordinates;
    }

    public Item getItem() {
        return item;
    }

    public ChunkCoordinates getCoordinates() {
        return coordinates;
    }
}
