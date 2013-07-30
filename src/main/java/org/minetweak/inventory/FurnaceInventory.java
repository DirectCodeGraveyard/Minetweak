package org.minetweak.inventory;

import net.minecraft.inventory.container.ContainerFurnace;
import org.minetweak.item.ItemStack;

public interface FurnaceInventory {
    public void setCooking(ItemStack stack);

    public ItemStack getCooking();

    public void setFuel(ItemStack stack);

    public ItemStack getFuel(ItemStack stack);

    public ContainerFurnace getContainer();
}
