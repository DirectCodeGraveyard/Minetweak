package org.minetweak.event.item;

import net.minecraft.item.ItemStack;
import org.minetweak.world.Location;

public class ItemBurnEvent extends ItemEvent {
    private Location location;

    public ItemBurnEvent(ItemStack itemStack, Location location) {
        super(new org.minetweak.item.ItemStack(itemStack));
        this.location = location;
    }

    /**
     * Gets the Location where this event occurred
     *
     * @return location
     */
    public Location getLocation() {
        return location;
    }
}
