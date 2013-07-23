package net.minecraft.material;

import net.minecraft.world.map.MapColor;

final class MaterialWeb extends Material {
    MaterialWeb(MapColor par1MapColor) {
        super(par1MapColor);
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean blocksMovement() {
        return false;
    }
}
