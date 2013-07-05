package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelTime implements Callable<String> {
    final WorldInfo worldInfoInstance;

    CallableLevelTime(WorldInfo par1WorldInfo) {
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
