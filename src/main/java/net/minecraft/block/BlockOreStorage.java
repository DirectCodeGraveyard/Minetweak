package net.minecraft.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.material.Material;

public class BlockOreStorage extends Block {
    public BlockOreStorage(int par1) {
        super(par1, Material.iron);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
