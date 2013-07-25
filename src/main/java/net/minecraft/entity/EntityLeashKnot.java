package net.minecraft.entity;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.utils.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class EntityLeashKnot extends EntityHanging {
    public EntityLeashKnot(World par1World) {
        super(par1World);
    }

    public EntityLeashKnot(World par1World, int par2, int par3, int par4) {
        super(par1World, par2, par3, par4, 0);
        this.setPosition((double) par2 + 0.5D, (double) par3 + 0.5D, (double) par4 + 0.5D);
    }

    protected void entityInit() {
        super.entityInit();
    }

    public void setDirection(int par1) {
    }

    public int func_82329_d() {
        return 9;
    }

    public int func_82330_g() {
        return 9;
    }

    public void func_110128_b(Entity par1Entity) {
    }

    /**
     * adds the ID of this entity to the NBT given
     */
    public boolean addEntityID(NBTTagCompound par1NBTTagCompound) {
        return false;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    }

    public boolean func_130002_c(EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.getHeldItem();
        boolean var3 = false;

        if (var2 != null && var2.itemID == Item.field_111214_ch.itemID && !this.worldObj.isRemote) {
            double var4 = 7.0D;
            List var6 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().getAABB(this.posX - var4, this.posY - var4, this.posZ - var4, this.posX + var4, this.posY + var4, this.posZ + var4));

            if (var6 != null) {

                for (Object aVar6 : var6) {
                    EntityLiving var8 = (EntityLiving) aVar6;

                    if (var8.func_110167_bD() && var8.func_110166_bE() == par1EntityPlayer) {
                        var8.func_110162_b(this, true);
                        var3 = true;
                    }
                }
            }
        }

        if (!this.worldObj.isRemote && !var3) {
            this.setDead();
        }

        return true;
    }

    /**
     * checks to make sure painting can be placed there
     */
    public boolean onValidSurface() {
        int var1 = this.worldObj.getBlockId(this.xPosition, this.yPosition, this.zPosition);
        return Block.blocksList[var1] != null && Block.blocksList[var1].getRenderType() == 11;
    }

    public static EntityLeashKnot func_110129_a(World par0World, int par1, int par2, int par3) {
        EntityLeashKnot var4 = new EntityLeashKnot(par0World, par1, par2, par3);
        var4.field_98038_p = true;
        par0World.spawnEntityInWorld(var4);
        return var4;
    }

    public static EntityLeashKnot func_110130_b(World par0World, int par1, int par2, int par3) {
        List var4 = par0World.getEntitiesWithinAABB(EntityLeashKnot.class, AxisAlignedBB.getAABBPool().getAABB((double) par1 - 1.0D, (double) par2 - 1.0D, (double) par3 - 1.0D, (double) par1 + 1.0D, (double) par2 + 1.0D, (double) par3 + 1.0D));

        if (var4 != null) {

            for (Object aVar4 : var4) {
                EntityLeashKnot var7 = (EntityLeashKnot) aVar4;

                if (var7.xPosition == par1 && var7.yPosition == par2 && var7.zPosition == par3) {
                    return var7;
                }
            }
        }

        return null;
    }
}
