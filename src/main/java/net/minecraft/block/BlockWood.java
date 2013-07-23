package net.minecraft.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.material.Material;

public class BlockWood extends Block {
    /**
     * The type of tree this block came from.
     */
    public static final String[] woodType = new String[]{"oak", "spruce", "birch", "jungle"};

    public BlockWood(int par1) {
        super(par1, Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int par1) {
        return par1;
    }
}
