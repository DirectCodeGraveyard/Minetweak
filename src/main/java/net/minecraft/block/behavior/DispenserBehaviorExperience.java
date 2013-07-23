package net.minecraft.block.behavior;

import net.minecraft.entity.EntityExpBottle;
import net.minecraft.src.IPosition;
import net.minecraft.src.IProjectile;
import net.minecraft.world.World;

final class DispenserBehaviorExperience extends BehaviorProjectileDispense {
    /**
     * Return the projectile entity spawned by this dispense behavior.
     */
    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition) {
        return new EntityExpBottle(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
    }

    protected float func_82498_a() {
        return super.func_82498_a() * 0.5F;
    }

    protected float func_82500_b() {
        return super.func_82500_b() * 1.25F;
    }
}
