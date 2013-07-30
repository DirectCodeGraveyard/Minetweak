package net.minecraft.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesIngots {
    private Object[][] recipeItems;

    public RecipesIngots() {
        this.recipeItems = new Object[][]{{Block.blockGold, new ItemStack(Item.ingotGold, 9)}, {Block.blockIron, new ItemStack(Item.ingotIron, 9)}, {Block.blockDiamond, new ItemStack(Item.diamond, 9)}, {Block.blockEmerald, new ItemStack(Item.emerald, 9)}, {Block.blockLapis, new ItemStack(Item.dyePowder, 9, 4)}, {Block.blockRedstone, new ItemStack(Item.redstone, 9)}, {Block.coalBlock, new ItemStack(Item.coal, 9, 0)}};
    }

    /**
     * Adds the ingot recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager par1CraftingManager) {
        for (Object[] recipeItem : this.recipeItems) {
            Block var3 = (Block) recipeItem[0];
            ItemStack var4 = (ItemStack) recipeItem[1];
            par1CraftingManager.addRecipe(new ItemStack(var3), "###", "###", "###", '#', var4);
            par1CraftingManager.addRecipe(var4, "#", '#', var3);
        }

        par1CraftingManager.addRecipe(new ItemStack(Item.ingotGold), "###", "###", "###", '#', Item.goldNugget);
        par1CraftingManager.addRecipe(new ItemStack(Item.goldNugget, 9), "#", '#', Item.ingotGold);
    }
}
