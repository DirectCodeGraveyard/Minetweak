package net.minecraft.utils.callable;

import net.minecraft.block.Block;

import java.util.concurrent.Callable;

public final class CallableBlockType implements Callable<String> {
    final int blockID;

    public CallableBlockType(int par1) {
        this.blockID = par1;
    }

    public String callBlockType() {
        try {
            return String.format("ID #%d (%s // %s)", this.blockID, Block.blocksList[this.blockID].getUnlocalizedName(), Block.blocksList[this.blockID].getClass().getCanonicalName());
        } catch (Throwable var2) {
            return "ID #" + this.blockID;
        }
    }

    @Override
    public String call() {
        return this.callBlockType();
    }
}
