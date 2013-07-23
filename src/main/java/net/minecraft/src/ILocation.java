package net.minecraft.src;

import net.minecraft.world.World;

public interface ILocation extends IPosition {
    World getWorld();
}
