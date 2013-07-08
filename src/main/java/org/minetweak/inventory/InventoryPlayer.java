package org.minetweak.inventory;

import org.minetweak.item.ItemStack;

public class InventoryPlayer {
    private net.minecraft.src.InventoryPlayer playerInventory;

    public InventoryPlayer(net.minecraft.src.InventoryPlayer playerInventory) {
        this.playerInventory = playerInventory;
    }

    public ItemStack getStackInSlot(Integer slotID) {
        net.minecraft.src.ItemStack stack = playerInventory.getStackInSlot(slotID);
        if (stack==null)
            return null;
        else
            return new ItemStack(stack);
    }

    public void setSlotContents(Integer slotID, ItemStack stack) {
        if (stack==null) {
            playerInventory.setInventorySlotContents(slotID, null);
        } else {
            playerInventory.setInventorySlotContents(slotID, stack.getItemStack());
        }
    }

    public Integer getSize() {
        return playerInventory.getSizeInventory();
    }

    public void clear() {
        for (int i = 0; i<getSize(); i++) {
            ItemStack stack = getStackInSlot(i);
            if (stack!=null) {
                setSlotContents(i, null);
            }
        }
    }
}
