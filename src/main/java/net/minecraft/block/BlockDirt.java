package net.minecraft.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.material.Material;

public class BlockDirt extends Block {
    protected BlockDirt(int par1) {
        super(par1, Material.ground);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
