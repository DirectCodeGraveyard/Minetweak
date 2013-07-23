package net.minecraft.utils.callable;

import net.minecraft.world.WorldInfo;

import java.util.concurrent.Callable;

public class CallableLevelSeed implements Callable<String> {
    final WorldInfo worldInfoInstance;

    public CallableLevelSeed(WorldInfo par1WorldInfo) {
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
