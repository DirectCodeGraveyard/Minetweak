package org.minetweak.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerChest;
import org.minetweak.item.ItemStack;

public class InventoryChest implements ContainerInventory {
    private ContainerChest chest;

    public InventoryChest(ContainerChest chest) {
        this.chest = chest;
    }

    @Override
    public int getSize() {
        return 16;
    }

    @Override
    public void setStackInSlot(Integer slotID, ItemStack stack) {
        chest.inventoryItemStacks.set(slotID, stack.getItemStack());
    }

    @Override
    public ItemStack getStackInSlot(Integer slotID) {
        return new ItemStack(chest.inventoryItemStacks.get(slotID));
    }

    @Override
    public void clear() {
        chest.inventoryItemStacks.clear();
    }

    @Override
    public IInventory getMCInventory() {
        return chest.getLowerChestInventory();
    }
}
