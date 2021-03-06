package net.minecraft.inventory;

import net.minecraft.entity.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryBasic implements IInventory {
    private String inventoryTitle;
    private int slotsCount;
    private ItemStack[] inventoryContents;
    private List<IInvBasic> inventories;
    private boolean bool;

    public InventoryBasic(String par1Str, boolean par2, int par3) {
        this.inventoryTitle = par1Str;
        this.bool = par2;
        this.slotsCount = par3;
        this.inventoryContents = new ItemStack[par3];
    }

    public void addSubInventory(IInvBasic par1IInvBasic) {
        if (this.inventories == null) {
            this.inventories = new ArrayList<IInvBasic>();
        }

        this.inventories.add(par1IInvBasic);
    }

    public void setEntity(IInvBasic par1IInvBasic) {
        this.inventories.remove(par1IInvBasic);
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.inventoryContents[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.inventoryContents[par1] != null) {
            ItemStack var3;

            if (this.inventoryContents[par1].stackSize <= par2) {
                var3 = this.inventoryContents[par1];
                this.inventoryContents[par1] = null;
                this.onInventoryChanged();
                return var3;
            } else {
                var3 = this.inventoryContents[par1].splitStack(par2);

                if (this.inventoryContents[par1].stackSize == 0) {
                    this.inventoryContents[par1] = null;
                }

                this.onInventoryChanged();
                return var3;
            }
        } else {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.inventoryContents[par1] != null) {
            ItemStack var2 = this.inventoryContents[par1];
            this.inventoryContents[par1] = null;
            return var2;
        } else {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.inventoryContents[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return this.slotsCount;
    }

    /**
     * Returns the name of the inventory.
     */
    @Override
    public String getInvName() {
        return this.inventoryTitle;
    }

    /**
     * If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's
     * language. Otherwise it will be used directly.
     */
    @Override
    public boolean isInvNameLocalized() {
        return this.bool;
    }

    public void setInventoryTitle(String par1Str) {
        this.bool = true;
        this.inventoryTitle = par1Str;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    /**
     * Called when an the contents of an ContainerInventory change, usually
     */
    @Override
    public void onInventoryChanged() {
        if (this.inventories != null) {
            for (IInvBasic inv : this.inventories) {
                inv.onInventoryChanged(this);
            }
        }
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return true;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    @Override
    public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
        return true;
    }
}
