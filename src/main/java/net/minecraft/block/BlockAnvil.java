package net.minecraft.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityFallingSand;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.material.Material;
import net.minecraft.src.IBlockAccess;
import net.minecraft.utils.MathHelper;
import net.minecraft.world.World;

public class BlockAnvil extends BlockSand {
    /**
     * List of types/statues the Anvil can be in.
     */
    public static final String[] statuses = new String[]{"intact", "slightlyDamaged", "veryDamaged"};

    protected BlockAnvil(int par1) {
        super(par1, Material.anvil);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
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
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
        int var7 = MathHelper.floor_double((double) (par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int var8 = par1World.getBlockMetadata(par2, par3, par4) >> 2;
        ++var7;
        var7 %= 4;

        if (var7 == 0) {
            par1World.setBlockMetadata(par2, par3, par4, 2 | var8 << 2, 2);
        }

        if (var7 == 1) {
            par1World.setBlockMetadata(par2, par3, par4, 3 | var8 << 2, 2);
        }

        if (var7 == 2) {
            par1World.setBlockMetadata(par2, par3, par4, var8 << 2, 2);
        }

        if (var7 == 3) {
            par1World.setBlockMetadata(par2, par3, par4, 1 | var8 << 2, 2);
        }
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        if (par1World.isRemote) {
            return true;
        } else {
            par5EntityPlayer.displayGUIAnvil(par2, par3, par4);
            return true;
        }
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return 35;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int par1) {
        return par1 >> 2;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 3;

        if (var5 != 3 && var5 != 1) {
            this.setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
        } else {
            this.setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
        }
    }

    /**
     * Called when the falling block entity for this block is created
     */
    @Override
    protected void onStartFalling(EntityFallingSand par1EntityFallingSand) {
        par1EntityFallingSand.setIsAnvil(true);
    }

    /**
     * Called when the falling block entity for this block hits the ground and turns back into a block
     */
    @Override
    public void onFinishFalling(World par1World, int par2, int par3, int par4, int par5) {
        par1World.playAuxSFX(1022, par2, par3, par4, 0);
    }
}
