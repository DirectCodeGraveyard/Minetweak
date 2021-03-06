package net.minecraft.material;

import net.minecraft.world.map.MapColor;

public class MaterialLiquid extends Material {
    public MaterialLiquid(MapColor par1MapColor) {
        super(par1MapColor);
        this.setReplaceable();
        this.setNoPushMobility();
    }

    /**
     * Returns if blocks of these materials are liquids.
     */
    @Override
    public boolean isLiquid() {
        return true;
    }

    /**
     * Returns if this material is considered solid or not
     */
    @Override
    public boolean blocksMovement() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
