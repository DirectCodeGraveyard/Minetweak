package net.minecraft.utils.callable;

import net.minecraft.world.WorldInfo;

import java.util.concurrent.Callable;

public class CallableLevelGamemode implements Callable<String> {
    final WorldInfo worldInfoInstance;

    public CallableLevelGamemode(WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }

    public String callLevelGameModeInfo() {
        return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", WorldInfo.getGameType(this.worldInfoInstance).getName(), WorldInfo.getGameType(this.worldInfoInstance).getID(), WorldInfo.func_85117_p(this.worldInfoInstance), WorldInfo.func_85131_q(this.worldInfoInstance));
    }

    @Override
    public String call() {
        return this.callLevelGameModeInfo();
    }
}
