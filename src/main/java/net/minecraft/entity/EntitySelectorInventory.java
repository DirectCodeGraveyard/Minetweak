package net.minecraft.entity;

import net.minecraft.inventory.IInventory;

public final class EntitySelectorInventory implements IEntitySelector {
    /**
     * Return whether the specified entity is applicable to this filter.
     */
    public boolean isEntityApplicable(Entity par1Entity) {
        return par1Entity instanceof IInventory && par1Entity.isEntityAlive();
    }
}
