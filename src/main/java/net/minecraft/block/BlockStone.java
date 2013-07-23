package net.minecraft.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.material.Material;

import java.util.Random;

public class BlockStone extends Block {
    public BlockStone(int par1) {
        super(par1, Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return cobblestone.blockID;
    }
}
