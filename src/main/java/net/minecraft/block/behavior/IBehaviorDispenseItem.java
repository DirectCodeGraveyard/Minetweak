package net.minecraft.block.behavior;

import net.minecraft.item.ItemStack;
import net.minecraft.src.IBlockSource;

public interface IBehaviorDispenseItem {
    IBehaviorDispenseItem itemDispenseBehaviorProvider = new BehaviorDispenseItemProvider();

    /**
     * Dispenses the specified ItemStack from a dispenser.
     */
    ItemStack dispense(IBlockSource var1, ItemStack var2);
}
