package net.minecraft.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPlayer;
import net.minecraft.item.ItemLeash;
import net.minecraft.material.Material;
import net.minecraft.src.IBlockAccess;
import net.minecraft.utils.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class BlockFence extends Block {
    private final String field_94464_a;

    public BlockFence(int par1, String par2Str, Material par3Material) {
        super(par1, par3Material);
        this.field_94464_a = par2Str;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     */
    @Override
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
        boolean var8 = this.canConnectFenceTo(par1World, par2, par3, par4 - 1);
        boolean var9 = this.canConnectFenceTo(par1World, par2, par3, par4 + 1);
        boolean var10 = this.canConnectFenceTo(par1World, par2 - 1, par3, par4);
        boolean var11 = this.canConnectFenceTo(par1World, par2 + 1, par3, par4);
        float var12 = 0.375F;
        float var13 = 0.625F;
        float var14 = 0.375F;
        float var15 = 0.625F;

        if (var8) {
            var14 = 0.0F;
        }

        if (var9) {
            var15 = 1.0F;
        }

        if (var8 || var9) {
            this.setBlockBounds(var12, 0.0F, var14, var13, 1.5F, var15);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }

        var14 = 0.375F;
        var15 = 0.625F;

        if (var10) {
            var12 = 0.0F;
        }

        if (var11) {
            var13 = 1.0F;
        }

        if (var10 || var11 || !var8 && !var9) {
            this.setBlockBounds(var12, 0.0F, var14, var13, 1.5F, var15);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }

        if (var8) {
            var14 = 0.0F;
        }

        if (var9) {
            var15 = 1.0F;
        }

        this.setBlockBounds(var12, 0.0F, var14, var13, 1.0F, var15);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        boolean var5 = this.canConnectFenceTo(par1IBlockAccess, par2, par3, par4 - 1);
        boolean var6 = this.canConnectFenceTo(par1IBlockAccess, par2, par3, par4 + 1);
        boolean var7 = this.canConnectFenceTo(par1IBlockAccess, par2 - 1, par3, par4);
        boolean var8 = this.canConnectFenceTo(par1IBlockAccess, par2 + 1, par3, par4);
        float var9 = 0.375F;
        float var10 = 0.625F;
        float var11 = 0.375F;
        float var12 = 0.625F;

        if (var5) {
            var11 = 0.0F;
        }

        if (var6) {
            var12 = 1.0F;
        }

        if (var7) {
            var9 = 0.0F;
        }

        if (var8) {
            var10 = 1.0F;
        }

        this.setBlockBounds(var9, 0.0F, var11, var10, 1.0F, var12);
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

    @Override
    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return 11;
    }

    /**
     * Returns true if the specified block can be connected by a fence
     */
    public boolean canConnectFenceTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        int var5 = par1IBlockAccess.getBlockId(par2, par3, par4);

        if (var5 != this.blockID && var5 != fenceGate.blockID) {
            Block var6 = blocksList[var5];
            return var6 != null && var6.blockMaterial.isOpaque() && var6.renderAsNormalBlock() && var6.blockMaterial != Material.pumpkin;
        } else {
            return true;
        }
    }

    public static boolean isIdAFence(int par0) {
        return par0 == fence.blockID || par0 == netherFence.blockID;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        return par1World.isRemote || ItemLeash.func_135066_a(par5EntityPlayer, par1World, par2, par3, par4);
    }
}
