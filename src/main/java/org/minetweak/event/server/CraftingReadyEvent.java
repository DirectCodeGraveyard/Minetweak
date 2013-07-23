package org.minetweak.event.server;

import net.minecraft.crafting.CraftingManager;

public class CraftingReadyEvent {
    private CraftingManager craftingManager;
    public CraftingReadyEvent(CraftingManager craftingManager) {
        this.craftingManager = craftingManager;
    }

    /**
     * Get CraftingManager
     * @return the crafting manager instance for the server
     */
    public CraftingManager getCraftingManager() {
        return craftingManager;
    }
}
