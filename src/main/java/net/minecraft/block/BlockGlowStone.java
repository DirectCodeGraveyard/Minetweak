package net.minecraft.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.material.Material;
import net.minecraft.utils.MathHelper;

import java.util.Random;

public class BlockGlowStone extends Block {
    public BlockGlowStone(int par1, Material par2Material) {
        super(par1, par2Material);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    @Override
    public int quantityDroppedWithBonus(int par1, Random par2Random) {
        return MathHelper.clamp_int(this.quantityDropped(par2Random) + par2Random.nextInt(par1 + 1), 1, 4);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random) {
        return 2 + par1Random.nextInt(3);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return Item.glowstone.itemID;
    }
}
