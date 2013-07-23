package net.minecraft.utils.callable;

import net.minecraft.world.WorldInfo;

import java.util.concurrent.Callable;

public class CallableLevelGeneratorOptions implements Callable<String> {
    final WorldInfo worldInfoInstance;

    public CallableLevelGeneratorOptions(WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }

    public String callLevelGeneratorOptions() {
        return WorldInfo.getWorldGeneratorOptions(this.worldInfoInstance);
    }

    @Override
    public String call() {
        return this.callLevelGeneratorOptions();
    }
}
