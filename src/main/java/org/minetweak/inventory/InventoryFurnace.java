package org.minetweak.inventory;

import net.minecraft.inventory.container.ContainerFurnace;
import org.minetweak.item.ItemStack;

public class InventoryFurnace implements FurnaceInventory {
    private ContainerFurnace furnace;

    public InventoryFurnace(ContainerFurnace furnace) {
        this.furnace = furnace;
    }

    @Override
    public void setCooking(ItemStack stack) {
        furnace.setCooking(stack.getItemStack());
    }

    @Override
    public ItemStack getCooking() {
        return new ItemStack(furnace.getCooking());
    }

    @Override
    public void setFuel(ItemStack stack) {
        furnace.setFuel(stack.getItemStack());
    }

    @Override
    public ItemStack getFuel(ItemStack stack) {
        return new ItemStack(furnace.getFuel());
    }

    @Override
    public ContainerFurnace getContainer() {
        return furnace;
    }
}
