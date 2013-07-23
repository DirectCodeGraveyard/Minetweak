package net.minecraft.utils.callable;

import net.minecraft.tileentity.TileEntity;

import java.util.concurrent.Callable;

public class CallableTileEntityName implements Callable<String> {
    final TileEntity theTileEntity;

    public CallableTileEntityName(TileEntity par1TileEntity) {
        this.theTileEntity = par1TileEntity;
    }

    public String callTileEntityName() {
        return TileEntity.getClassToNameMap().get(this.theTileEntity.getClass()) + " // " + this.theTileEntity.getClass().getCanonicalName();
    }

    @Override
    public String call() {
        return this.callTileEntityName();
    }
}
