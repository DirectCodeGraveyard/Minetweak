package net.minecraft.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.material.Material;

public class BlockStoneBrick extends Block {
    public static final String[] STONE_BRICK_TYPES = new String[]{"default", "mossy", "cracked", "chiseled"};
    public static final String[] field_94407_b = new String[]{null, "mossy", "cracked", "carved"};

    public BlockStoneBrick(int par1) {
        super(par1, Material.rock);
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
