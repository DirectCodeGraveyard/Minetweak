package net.minecraft.utils.callable;

import net.minecraft.world.WorldInfo;

import java.util.concurrent.Callable;

public class CallableLevelDimension implements Callable<String> {
    final WorldInfo worldInfoInstance;

    public CallableLevelDimension(WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }

    public String callLevelDimension() {
        return String.valueOf(WorldInfo.func_85122_i(this.worldInfoInstance));
    }

    @Override
    public String call() {
        return this.callLevelDimension();
    }
}
