package net.minecraft.block.behavior;

import net.minecraft.entity.EntitySnowball;
import net.minecraft.src.IPosition;
import net.minecraft.src.IProjectile;
import net.minecraft.world.World;

final class DispenserBehaviorSnowball extends BehaviorProjectileDispense {
    /**
     * Return the projectile entity spawned by this dispense behavior.
     */
    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition) {
        return new EntitySnowball(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
    }
}
