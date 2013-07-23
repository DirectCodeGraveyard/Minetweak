package net.minecraft.inventory;

import net.minecraft.inventory.InventoryBasic;

public interface IInvBasic {
    /**
     * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
     */
    void onInventoryChanged(InventoryBasic var1);
}
