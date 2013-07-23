package net.minecraft.src;

import net.minecraft.entity.IEntitySelector;

public interface IMob extends IAnimals {
    /**
     * Entity selector for IMob types.
     */
    IEntitySelector mobSelector = new FilterIMob();
}
