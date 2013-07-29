package org.minetweak.inventory;

import net.minecraft.inventory.IInventory;
import org.minetweak.item.ItemStack;

public interface Inventory {

    /**
     * Gets Inventory size
     *
     * @return size of inventory
     */
    public int getSize();

    /**
     * Sets a slots contents
     *
     * @param slotID id of slot
     * @param stack  stack to set
     */
    public void setStackInSlot(Integer slotID, ItemStack stack);

    /**
     * Gets stack in slot
     *
     * @param slotID id of slot
     * @return itemstack
     */
    public ItemStack getStackInSlot(Integer slotID);

    /**
     * Clear inventory
     */
    public void clear();

    /**
     * Gets the MC Inventory. Used to display the Inventory to the player
     *
     * @return MC Inventory
     */
    public IInventory getMCInventory();
}
