package net.minecraft.block;

import net.minecraft.entity.EntityItem;
import net.minecraft.material.Material;
import net.minecraft.utils.MathHelper;
import net.minecraft.world.World;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate {
    /**
     * The maximum number of items the plate weights.
     */
    private final int maxItemsWeighted;

    protected BlockPressurePlateWeighted(int par1, String par2Str, Material par3Material, int par4) {
        super(par1, par2Str, par3Material);
        this.maxItemsWeighted = par4;
    }

    /**
     * Returns the current state of the pressure plate. Returns a value between 0 and 15 based on the number of items on
     * it.
     */
    @Override
    protected int getPlateState(World par1World, int par2, int par3, int par4) {
        int var5 = 0;

        for (Object o : par1World.getEntitiesWithinAABB(EntityItem.class, this.getSensitiveAABB(par2, par3, par4))) {
            EntityItem var7 = (EntityItem) o;
            var5 += var7.getEntityItem().stackSize;

            if (var5 >= this.maxItemsWeighted) {
                break;
            }
        }

        if (var5 <= 0) {
            return 0;
        } else {
            float var8 = (float) Math.min(this.maxItemsWeighted, var5) / (float) this.maxItemsWeighted;
            return MathHelper.ceiling_float_int(var8 * 15.0F);
        }
    }

    /**
     * Argument is metadata. Returns power level (0-15)
     */
    @Override
    protected int getPowerSupply(int par1) {
        return par1;
    }

    /**
     * Argument is weight (0-15). Return the metadata to be set because of it.
     */
    @Override
    protected int getMetaFromWeight(int par1) {
        return par1;
    }

    /**
     * How many world ticks before ticking
     */
    @Override
    public int tickRate(World par1World) {
        return 10;
    }
}
