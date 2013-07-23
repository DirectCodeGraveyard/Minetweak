package net.minecraft.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.utils.enums.EnumSkyBlock;
import net.minecraft.material.Material;
import net.minecraft.src.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDaylightDetector;
import net.minecraft.utils.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDaylightDetector extends BlockContainer {
    private Icon[] iconArray = new Icon[2];

    public BlockDaylightDetector(int par1) {
        super(par1, Material.wood);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
    }

    /**
     * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
     * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
     * Y, Z, side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    @Override
    public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        return par1IBlockAccess.getBlockMetadata(par2, par3, par4);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
    }

    public void updateLightLevel(World par1World, int par2, int par3, int par4) {
        if (!par1World.provider.hasNoSky) {
            int var5 = par1World.getBlockMetadata(par2, par3, par4);
            int var6 = par1World.getSavedLightValue(EnumSkyBlock.Sky, par2, par3, par4) - par1World.skylightSubtracted;
            float var7 = par1World.getCelestialAngleRadians(1.0F);

            if (var7 < (float) Math.PI) {
                var7 += (0.0F - var7) * 0.2F;
            } else {
                var7 += (((float) Math.PI * 2F) - var7) * 0.2F;
            }

            var6 = Math.round((float) var6 * MathHelper.cos(var7));

            if (var6 < 0) {
                var6 = 0;
            }

            if (var6 > 15) {
                var6 = 15;
            }

            if (var5 != var6) {
                par1World.setBlockMetadata(par2, par3, par4, var6, 3);
            }
        }
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock() {
        return false;
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
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    @Override
    public boolean canProvidePower() {
        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World) {
        return new TileEntityDaylightDetector();
    }
}
