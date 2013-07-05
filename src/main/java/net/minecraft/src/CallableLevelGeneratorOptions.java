package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelGeneratorOptions implements Callable<String> {
    final WorldInfo worldInfoInstance;

    CallableLevelGeneratorOptions(WorldInfo par1WorldInfo) {
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
