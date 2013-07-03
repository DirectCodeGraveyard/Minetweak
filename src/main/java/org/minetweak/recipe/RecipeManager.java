package org.minetweak.recipe;

import com.google.common.eventbus.Subscribe;
import net.minecraft.src.*;
import org.minetweak.event.server.CraftingReadyEvent;

public class RecipeManager {
    private static final RecipeManager instance = new RecipeManager();
    private CraftingManager craftingManager;

    /**
     * Get the global RecipeManager instance
     * @return the instance of RecipeManager
     */
    public static RecipeManager getInstance() {
        return instance;
    }

    /**
     * Adds a shaped recipe
     * @param output output of recipe
     * @param data the item list and string values
     */
    public void addRecipe(ItemStack output, Object... data) {
        craftingManager.addRecipe(output, data);
    }

    /**
     * Adds a shapeless recipe
     * @param output output of recipe
     * @param inputs input list
     */
    public void addShapelessRecipe(ItemStack output, Object... inputs) {
        craftingManager.addShapelessRecipe(output, inputs);
    }

    /**
     * Adds a furnace recipe
     * @param input the input
     * @param output the output
     * @param xp amount of XP given
     */
    public void addFurnaceRecipe(int input, ItemStack output, float xp) {
        FurnaceRecipes.smelting().addSmelting(input, output, xp);
    }

    /**
     * Callback to set the CraftingManager
     * @param event event fired
     */
    @Subscribe
    public void craftingReadyCallback(CraftingReadyEvent event) {
        this.craftingManager = event.getCraftingManager();
    }
}
