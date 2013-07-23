package net.minecraft.utils.callable;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import java.util.concurrent.Callable;

public class CallableTileEntityID implements Callable<String> {
    final TileEntity theTileEntity;

    public CallableTileEntityID(TileEntity par1TileEntity) {
        this.theTileEntity = par1TileEntity;
    }

    public String callTileEntityID() {
        int var1 = this.theTileEntity.worldObj.getBlockId(this.theTileEntity.xCoord, this.theTileEntity.yCoord, this.theTileEntity.zCoord);

        try {
            return String.format("ID #%d (%s // %s)", var1, Block.blocksList[var1].getUnlocalizedName(), Block.blocksList[var1].getClass().getCanonicalName());
        } catch (Throwable var3) {
            return "ID #" + var1;
        }
    }

    @Override
    public String call() {
        return this.callTileEntityID();
    }
}
