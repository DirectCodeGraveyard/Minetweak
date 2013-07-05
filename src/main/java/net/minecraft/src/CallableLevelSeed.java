package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelSeed implements Callable<String> {
    final WorldInfo worldInfoInstance;

    CallableLevelSeed(WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }

    public String callLevelSeed() {
        return String.valueOf(this.worldInfoInstance.getSeed());
    }

    @Override
    public String call() {
        return this.callLevelSeed();
    }
}
