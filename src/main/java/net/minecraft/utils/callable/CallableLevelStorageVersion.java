package net.minecraft.utils.callable;

import net.minecraft.world.WorldInfo;

import java.util.concurrent.Callable;

public class CallableLevelStorageVersion implements Callable<String> {
    final WorldInfo worldInfoInstance;

    public CallableLevelStorageVersion(WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }

    public String callLevelStorageFormat() {
        String var1 = "Unknown?";

        try {
            switch (WorldInfo.getSaveVersion(this.worldInfoInstance)) {
                case 19132:
                    var1 = "McRegion";
                    break;

                case 19133:
                    var1 = "Anvil";
            }
        } catch (Throwable ignored) {
        }

        return String.format("0x%05X - %s", WorldInfo.getSaveVersion(this.worldInfoInstance), var1);
    }

    @Override
    public String call() {
        return this.callLevelStorageFormat();
    }
}
