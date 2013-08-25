package org.minetweak.recipe;

import com.google.common.eventbus.Subscribe;
import net.minecraft.crafting.CraftingManager;
import net.minecraft.crafting.FurnaceRecipes;
import net.minecraft.item.ItemStack;
import org.minetweak.event.server.CraftingReadyEvent;

/**
 * Manages Recipes
 * NOTE; This currently is buggy, but will be improved.
 */
public class RecipeManager {
    private static final RecipeManager instance = new RecipeManager();
    private CraftingManager craftingManager;

    /**
     * Get the global RecipeManager instance
     *
     * @return the instance of RecipeManager
     */
    public static RecipeManager getInstance() {
        return instance;
    }

    /**
     * Adds a shaped crafting
     *
     * @param output output of crafting
     * @param data   the item list and string values
     */
    public void addRecipe(ItemStack output, Object... data) {
        craftingManager.addRecipe(output, data);
    }

    /**
     * Adds a shapeless crafting
     *
     * @param output output of crafting
     * @param inputs input list
     */
    public void addShapelessRecipe(ItemStack output, Object... inputs) {
        craftingManager.addShapelessRecipe(output, inputs);
    }

    /**
     * Adds a furnace crafting
     *
     * @param input  the input
     * @param output the output
     * @param xp     amount of XP given
     */
    public void addFurnaceRecipe(int input, ItemStack output, float xp) {
        FurnaceRecipes.smelting().addSmelting(input, output, xp);
    }

    /**
     * Callback to set the CraftingManager
     *
     * @param event event fired
     */
    @Subscribe
    public void craftingReadyCallback(CraftingReadyEvent event) {
        this.craftingManager = event.getCraftingManager();
        // addShapelessRecipe(new ItemStack(Item.appleRed), new ItemStack(Item.stick));
    }
}
