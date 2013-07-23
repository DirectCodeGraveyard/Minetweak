package net.minecraft.block.behavior;

import net.minecraft.item.ItemStack;
import net.minecraft.src.IBlockSource;

final class BehaviorDispenseItemProvider implements IBehaviorDispenseItem {
    /**
     * Dispenses the specified ItemStack from a dispenser.
     */
    public ItemStack dispense(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
        return par2ItemStack;
    }
}
