package org.minetweak.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import org.minetweak.entity.Player;
import org.minetweak.item.ItemStack;

/**
 * Represents an EnderChest
 */
public class EnderChest implements ContainerInventory {

    private InventoryEnderChest chest;

    public EnderChest(Player player) {
        this.chest = (InventoryEnderChest) player.getEnderChest().getMCInventory();
    }

    public EnderChest(InventoryEnderChest chest) {
        this.chest = chest;
    }

    @Override
    public int getSize() {
        return chest.getSizeInventory();
    }

    @Override
    public void setStackInSlot(Integer slotID, ItemStack stack) {
        chest.setInventorySlotContents(slotID, stack.getItemStack());
    }

    @Override
    public ItemStack getStackInSlot(Integer slotID) {
        return new ItemStack(chest.getStackInSlot(slotID));
    }

    @Override
    public void clear() {
        int size = chest.getSizeInventory();
        for (int i = 0; i < size; i++) {
            setStackInSlot(i, null);
        }
    }

    @Override
    public IInventory getMCInventory() {
        return chest;
    }
}
