package net.minecraft.entity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.map.MapData;

public class EntityItemFrame extends EntityHanging {
    /**
     * Chance for this item frame's item to drop from the frame.
     */
    private float itemDropChance = 1.0F;

    public EntityItemFrame(World par1World) {
        super(par1World);
    }

    public EntityItemFrame(World par1World, int par2, int par3, int par4, int par5) {
        super(par1World, par2, par3, par4, par5);
        this.setDirection(par5);
    }

    protected void entityInit() {
        this.getDataWatcher().addObjectByDataType(2, 5);
        this.getDataWatcher().addObject(3, (byte) 0);
    }

    public int func_82329_d() {
        return 9;
    }

    public int func_82330_g() {
        return 9;
    }

    public void func_110128_b(Entity par1Entity) {
        ItemStack var2 = this.getDisplayedItem();

        if (par1Entity instanceof EntityPlayer) {
            EntityPlayer var3 = (EntityPlayer) par1Entity;

            if (var3.capabilities.isCreativeMode) {
                this.func_110131_b(var2);
                return;
            }
        }

        this.entityDropItem(new ItemStack(Item.itemFrame), 0.0F);

        if (var2 != null && this.rand.nextFloat() < this.itemDropChance) {
            var2 = var2.copy();
            this.func_110131_b(var2);
            this.entityDropItem(var2, 0.0F);
        }
    }

    private void func_110131_b(ItemStack par1ItemStack) {
        if (par1ItemStack != null) {
            if (par1ItemStack.itemID == Item.map.itemID) {
                MapData var2 = ((ItemMap) par1ItemStack.getItem()).getMapData(par1ItemStack, this.worldObj);
                var2.playersVisibleOnMap.remove("frame-" + this.entityId);
            }

            par1ItemStack.setItemFrame(null);
        }
    }

    public ItemStack getDisplayedItem() {
        return this.getDataWatcher().getWatchableObjectItemStack(2);
    }

    public void setDisplayedItem(ItemStack par1ItemStack) {
        par1ItemStack = par1ItemStack.copy();
        par1ItemStack.stackSize = 1;
        par1ItemStack.setItemFrame(this);
        this.getDataWatcher().updateObject(2, par1ItemStack);
        this.getDataWatcher().setObjectWatched(2);
    }

    /**
     * Return the rotation of the item currently on this frame.
     */
    public int getRotation() {
        return this.getDataWatcher().getWatchableObjectByte(3);
    }

    public void setItemRotation(int par1) {
        this.getDataWatcher().updateObject(3, (byte) (par1 % 4));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        if (this.getDisplayedItem() != null) {
            par1NBTTagCompound.setCompoundTag("Item", this.getDisplayedItem().writeToNBT(new NBTTagCompound()));
            par1NBTTagCompound.setByte("ItemRotation", (byte) this.getRotation());
            par1NBTTagCompound.setFloat("ItemDropChance", this.itemDropChance);
        }

        super.writeEntityToNBT(par1NBTTagCompound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Item");

        if (var2 != null && !var2.hasNoTags()) {
            this.setDisplayedItem(ItemStack.loadItemStackFromNBT(var2));
            this.setItemRotation(par1NBTTagCompound.getByte("ItemRotation"));

            if (par1NBTTagCompound.hasKey("ItemDropChance")) {
                this.itemDropChance = par1NBTTagCompound.getFloat("ItemDropChance");
            }
        }

        super.readEntityFromNBT(par1NBTTagCompound);
    }

    public boolean func_130002_c(EntityPlayer par1EntityPlayer) {
        if (this.getDisplayedItem() == null) {
            ItemStack var2 = par1EntityPlayer.getHeldItem();

            if (var2 != null && !this.worldObj.isRemote) {
                this.setDisplayedItem(var2);

                if (!par1EntityPlayer.capabilities.isCreativeMode && --var2.stackSize <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
                }
            }
        } else if (!this.worldObj.isRemote) {
            this.setItemRotation(this.getRotation() + 1);
        }

        return true;
    }
}
