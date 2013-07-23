package net.minecraft.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IRecipe {
    /**
     * Used to check if a crafting matches current crafting inventory
     */
    boolean matches(InventoryCrafting var1, World var2);

    /**
     * Returns an Item that is the result of this crafting
     */
    ItemStack getCraftingResult(InventoryCrafting var1);

    /**
     * Returns the size of the crafting area
     */
    int getRecipeSize();

    ItemStack getRecipeOutput();
}
