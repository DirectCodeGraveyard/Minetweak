package org.minetweak.inventory;

import net.minecraft.inventory.IInventory;
import org.minetweak.item.ItemStack;

public class InventoryPlayer implements ContainerInventory {
    private net.minecraft.inventory.InventoryPlayer playerInventory;

    /**
     * Creates an ContainerInventory from MC's ContainerInventory Player
     *
     * @param playerInventory Minetweak ContainerInventory
     */
    public InventoryPlayer(net.minecraft.inventory.InventoryPlayer playerInventory) {
        this.playerInventory = playerInventory;
    }

    @Override
    public ItemStack getStackInSlot(Integer slotID) {
        net.minecraft.item.ItemStack stack = playerInventory.getStackInSlot(slotID);
        if (stack == null)
            return null;
        else
            return new ItemStack(stack);
    }

    @Override
    public int getSize() {
        return playerInventory.getSizeInventory();
    }


    @Override
    public void setStackInSlot(Integer slotId, ItemStack stack) {
        if (stack == null) {
            playerInventory.setInventorySlotContents(slotId, null);
        } else {
            playerInventory.setInventorySlotContents(slotId, stack.getItemStack());
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < getSize(); i++) {
            ItemStack stack = getStackInSlot(i);
            if (stack != null) {
                setStackInSlot(i, null);
            }
        }
    }

    public IInventory getMCInventory() {
        return playerInventory;
    }
}
