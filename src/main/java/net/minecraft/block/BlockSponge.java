package net.minecraft.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.material.Material;

public class BlockSponge extends Block {
    protected BlockSponge(int par1) {
        super(par1, Material.sponge);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
