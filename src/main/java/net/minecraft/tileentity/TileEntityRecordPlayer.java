package net.minecraft.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityRecordPlayer extends TileEntity {
    /**
     * ID of record which is in Jukebox
     */
    private ItemStack record;

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("RecordItem")) {
            this.func_110135_a(ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("RecordItem")));
        } else if (par1NBTTagCompound.getInteger("Record") > 0) {
            this.func_110135_a(new ItemStack(par1NBTTagCompound.getInteger("Record"), 1, 0));
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);

        if (this.func_110136_a() != null) {
            par1NBTTagCompound.setCompoundTag("RecordItem", this.func_110136_a().writeToNBT(new NBTTagCompound()));
            par1NBTTagCompound.setInteger("Record", this.func_110136_a().itemID);
        }
    }

    public ItemStack func_110136_a() {
        return this.record;
    }

    public void func_110135_a(ItemStack par1ItemStack) {
        this.record = par1ItemStack;
        this.onInventoryChanged();
    }
}
