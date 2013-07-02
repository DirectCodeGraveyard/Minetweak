package org.minetweak.recipe;

import com.google.common.eventbus.Subscribe;
import net.minecraft.src.*;
import org.minetweak.event.server.CraftingReadyEvent;

public class RecipeManager {
    private static final RecipeManager instance = new RecipeManager();
    private CraftingManager craftingManager;

    public static RecipeManager getInstance() {
        return instance;
    }

    public void addRecipe(ItemStack output, Object... data) {
        craftingManager.addRecipe(output, data);
    }

    public void addShapelessRecipe(ItemStack output, Object... inputs) {
        craftingManager.addShapelessRecipe(output, inputs);
    }

    public void addFurnaceRecipe(int amount, ItemStack output, float xp) {
        FurnaceRecipes.smelting().addSmelting(amount, output, xp);
    }

    @Subscribe
    public void craftingReadyCallback(CraftingReadyEvent event) {
        this.craftingManager = event.getCraftingManager();
    }
}
