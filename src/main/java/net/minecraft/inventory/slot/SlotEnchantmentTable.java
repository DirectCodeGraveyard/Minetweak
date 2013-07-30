package net.minecraft.inventory.slot;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.container.ContainerEnchantment;
import net.minecraft.item.ItemStack;

public class SlotEnchantmentTable extends InventoryBasic {
    /**
     * The brewing stand this slot belongs to.
     */
    final ContainerEnchantment container;

    public SlotEnchantmentTable(ContainerEnchantment par1ContainerEnchantment, String par2Str, boolean par3, int par4) {
        super(par2Str, par3, par4);
        this.container = par1ContainerEnchantment;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit() {
        return 1;
    }

    /**
     * Called when an the contents of an ContainerInventory change, usually
     */
    public void onInventoryChanged() {
        super.onInventoryChanged();
        this.container.onCraftMatrixChanged(this);
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
        return true;
    }
}
