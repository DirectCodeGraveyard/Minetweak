package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableTileEntityName implements Callable<String> {
    final TileEntity theTileEntity;

    CallableTileEntityName(TileEntity par1TileEntity) {
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
