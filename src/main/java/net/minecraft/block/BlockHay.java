package net.minecraft.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.material.Material;

public class BlockHay extends BlockRotatedPillar {
    public BlockHay(int par1) {
        super(par1, Material.grass);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return 31;
    }
}
