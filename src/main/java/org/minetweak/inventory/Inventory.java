package org.minetweak.inventory;

import org.minetweak.item.ItemStack;

public interface Inventory {

    /**
     * Gets Inventory size
     * @return size of inventory
     */
    public Integer getSize();

    /**
     * Sets a slots contents
     * @param slotID id of slot
     * @param stack stack to set
     */
    public void setStackInSlot(Integer slotID, ItemStack stack);

    /**
     * Gets stack in slot
     * @param slotID id of slot
     * @return itemstack
     */
    public ItemStack getStackInSlot(Integer slotID);

    /**
     * Clear inventory
     */
    public void clear();
}
