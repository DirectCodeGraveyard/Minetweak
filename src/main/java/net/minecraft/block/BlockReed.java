package net.minecraft.block;

import net.minecraft.item.Item;
import net.minecraft.material.Material;
import net.minecraft.utils.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.Random;

public class BlockReed extends Block {
    protected BlockReed(int par1) {
        super(par1, Material.plants);
        float var2 = 0.375F;
        this.setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 1.0F, 0.5F + var2);
        this.setTickRandomly(true);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (par1World.isAirBlock(par2, par3 + 1, par4)) {
            int var6;

            var6 = 1;
            while (par1World.getBlockId(par2, par3 - var6, par4) == this.blockID) {
                ++var6;
            }

            if (var6 < 3) {
                int var7 = par1World.getBlockMetadata(par2, par3, par4);

                if (var7 == 15) {
                    par1World.setBlock(par2, par3 + 1, par4, this.blockID);
                    par1World.setBlockMetadata(par2, par3, par4, 0, 4);
                } else {
                    par1World.setBlockMetadata(par2, par3, par4, var7 + 1, 4);
                }
            }
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        int var5 = par1World.getBlockId(par2, par3 - 1, par4);
        return var5 == this.blockID || (!(var5 != grass.blockID && var5 != dirt.blockID && var5 != sand.blockID) && (par1World.getBlockMaterial(par2 - 1, par3 - 1, par4) == Material.water || (par1World.getBlockMaterial(par2 + 1, par3 - 1, par4) == Material.water || (par1World.getBlockMaterial(par2, par3 - 1, par4 - 1) == Material.water || par1World.getBlockMaterial(par2, par3 - 1, par4 + 1) == Material.water))));
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
        this.checkBlockCoordValid(par1World, par2, par3, par4);
    }

    /**
     * Checks if current block pos is valid, if not, breaks the block as dropable item. Used for reed and cactus.
     */
    protected final void checkBlockCoordValid(World par1World, int par2, int par3, int par4) {
        if (!this.canBlockStay(par1World, par2, par3, par4)) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    @Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
        return this.canPlaceBlockAt(par1World, par2, par3, par4);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return Item.reed.itemID;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return 1;
    }
}
