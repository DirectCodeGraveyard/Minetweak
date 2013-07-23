package net.minecraft.src;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntitySelector;

final class FilterIMob implements IEntitySelector {
    /**
     * Return whether the specified entity is applicable to this filter.
     */
    public boolean isEntityApplicable(Entity par1Entity) {
        return par1Entity instanceof IMob;
    }
}
