package net.minecraft.utils.callable;

import net.minecraft.world.WorldInfo;

import java.util.concurrent.Callable;

public class CallableLevelTime implements Callable<String> {
    final WorldInfo worldInfoInstance;

    public CallableLevelTime(WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }

    public String callLevelTime() {
        return String.format("%d game time, %d day time", WorldInfo.func_85126_g(this.worldInfoInstance), WorldInfo.getWorldTime(this.worldInfoInstance));
    }

    @Override
    public String call() {
        return this.callLevelTime();
    }
}
