package org.minetweak.container;

import net.minecraft.src.InventoryPlayer;
import org.minetweak.item.ItemStack;

public class Inventory {
    private InventoryPlayer playerInventory;

    public Inventory(InventoryPlayer playerInventory) {
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
