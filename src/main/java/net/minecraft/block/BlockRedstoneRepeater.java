package net.minecraft.block;

import net.minecraft.entity.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.src.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRedstoneRepeater extends BlockRedstoneLogic {
    /**
     * The offsets for the two torches in redstone repeater blocks.
     */
    public static final double[] repeaterTorchOffset = new double[]{-0.0625D, 0.0625D, 0.1875D, 0.3125D};

    /**
     * The states in which the redstone repeater blocks can be.
     */
    private static final int[] repeaterState = new int[]{1, 2, 3, 4};

    protected BlockRedstoneRepeater(int par1, boolean par2) {
        super(par1, par2);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        int var10 = par1World.getBlockMetadata(par2, par3, par4);
        int var11 = (var10 & 12) >> 2;
        var11 = var11 + 1 << 2 & 12;
        par1World.setBlockMetadata(par2, par3, par4, var11 | var10 & 3, 3);
        return true;
    }

    @Override
    protected int func_94481_j_(int par1) {
        return repeaterState[(par1 & 12) >> 2] * 2;
    }

    @Override
    protected BlockRedstoneLogic func_94485_e() {
        return redstoneRepeaterActive;
    }

    @Override
    protected BlockRedstoneLogic func_94484_i() {
        return redstoneRepeaterIdle;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return Item.redstoneRepeater.itemID;
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return 15;
    }

    @Override
    public boolean func_94476_e(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        return this.func_94482_f(par1IBlockAccess, par2, par3, par4, par5) > 0;
    }

    @Override
    protected boolean func_94477_d(int par1) {
        return isRedstoneRepeaterBlockID(par1);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        this.func_94483_i_(par1World, par2, par3, par4);
    }
}
