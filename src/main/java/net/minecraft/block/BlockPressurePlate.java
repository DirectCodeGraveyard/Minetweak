package net.minecraft.block;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityPlayer;
import net.minecraft.material.Material;
import net.minecraft.utils.enums.EnumMobType;
import net.minecraft.world.World;

import java.util.List;

public class BlockPressurePlate extends BlockBasePressurePlate {
    /**
     * The mob type that can trigger this pressure plate.
     */
    private EnumMobType triggerMobType;

    protected BlockPressurePlate(int par1, String par2Str, Material par3Material, EnumMobType par4EnumMobType) {
        super(par1, par2Str, par3Material);
        this.triggerMobType = par4EnumMobType;
    }

    /**
     * Argument is weight (0-15). Return the metadata to be set because of it.
     */
    @Override
    protected int getMetaFromWeight(int par1) {
        return par1 > 0 ? 1 : 0;
    }

    /**
     * Argument is metadata. Returns power level (0-15)
     */
    @Override
    protected int getPowerSupply(int par1) {
        return par1 == 1 ? 15 : 0;
    }

    /**
     * Returns the current state of the pressure plate. Returns a value between 0 and 15 based on the number of items on
     * it.
     */
    @Override
    protected int getPlateState(World par1World, int par2, int par3, int par4) {
        List var5 = null;

        if (this.triggerMobType == EnumMobType.everything) {
            var5 = par1World.getEntitiesWithinAABBExcludingEntity(null, this.getSensitiveAABB(par2, par3, par4));
        }

        if (this.triggerMobType == EnumMobType.mobs) {
            var5 = par1World.getEntitiesWithinAABB(EntityLivingBase.class, this.getSensitiveAABB(par2, par3, par4));
        }

        if (this.triggerMobType == EnumMobType.players) {
            var5 = par1World.getEntitiesWithinAABB(EntityPlayer.class, this.getSensitiveAABB(par2, par3, par4));
        }

        if (var5 != null && !var5.isEmpty()) {
            for (Object aVar5 : var5) {
                Entity var7 = (Entity) aVar5;

                if (!var7.doesEntityNotTriggerPressurePlate()) {
                    return 15;
                }
            }
        }

        return 0;
    }
}
