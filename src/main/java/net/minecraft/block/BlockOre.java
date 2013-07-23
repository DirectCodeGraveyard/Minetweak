package net.minecraft.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.material.Material;
import net.minecraft.utils.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOre extends Block {
    public BlockOre(int par1) {
        super(par1, Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return this.blockID == oreCoal.blockID ? Item.coal.itemID : (this.blockID == oreDiamond.blockID ? Item.diamond.itemID : (this.blockID == oreLapis.blockID ? Item.dyePowder.itemID : (this.blockID == oreEmerald.blockID ? Item.emerald.itemID : (this.blockID == oreNetherQuartz.blockID ? Item.netherQuartz.itemID : this.blockID))));
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random) {
        return this.blockID == oreLapis.blockID ? 4 + par1Random.nextInt(5) : 1;
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    @Override
    public int quantityDroppedWithBonus(int par1, Random par2Random) {
        if (par1 > 0 && this.blockID != this.idDropped(0, par2Random, par1)) {
            int var3 = par2Random.nextInt(par1 + 2) - 1;

            if (var3 < 0) {
                var3 = 0;
            }

            return this.quantityDropped(par2Random) * (var3 + 1);
        } else {
            return this.quantityDropped(par2Random);
        }
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    @Override
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);

        if (this.idDropped(par5, par1World.rand, par7) != this.blockID) {
            int var8 = 0;

            if (this.blockID == oreCoal.blockID) {
                var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 0, 2);
            } else if (this.blockID == oreDiamond.blockID) {
                var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 3, 7);
            } else if (this.blockID == oreEmerald.blockID) {
                var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 3, 7);
            } else if (this.blockID == oreLapis.blockID) {
                var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 2, 5);
            } else if (this.blockID == oreNetherQuartz.blockID) {
                var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 2, 5);
            }

            this.dropXpOnBlockBreak(par1World, par2, par3, par4, var8);
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int par1) {
        return this.blockID == oreLapis.blockID ? 4 : 0;
    }
}
