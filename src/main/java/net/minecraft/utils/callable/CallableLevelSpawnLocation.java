package net.minecraft.utils.callable;

import net.minecraft.crash.CrashReportCategory;
import net.minecraft.world.WorldInfo;

import java.util.concurrent.Callable;

public class CallableLevelSpawnLocation implements Callable<String> {
    final WorldInfo worldInfoInstance;

    public CallableLevelSpawnLocation(WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }

    public String callLevelSpawnLocation() {
        return CrashReportCategory.getLocationInfo(WorldInfo.getSpawnXCoordinate(this.worldInfoInstance), WorldInfo.getSpawnYCoordinate(this.worldInfoInstance), WorldInfo.getSpawnZCoordinate(this.worldInfoInstance));
    }

    @Override
    public String call() {
        return this.callLevelSpawnLocation();
    }
}
