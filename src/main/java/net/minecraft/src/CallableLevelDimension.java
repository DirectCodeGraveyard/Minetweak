package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelDimension implements Callable<String> {
    final WorldInfo worldInfoInstance;

    CallableLevelDimension(WorldInfo par1WorldInfo) {
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
