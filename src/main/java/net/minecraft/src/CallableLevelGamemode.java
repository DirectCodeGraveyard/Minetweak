package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelGamemode implements Callable
{
    final WorldInfo worldInfoInstance;

    CallableLevelGamemode(WorldInfo par1WorldInfo)
    {
        this.worldInfoInstance = par1WorldInfo;
    }

    public String callLevelGameModeInfo()
    {
        return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", WorldInfo.getGameType(this.worldInfoInstance).getName(), WorldInfo.getGameType(this.worldInfoInstance).getID(), WorldInfo.func_85117_p(this.worldInfoInstance), WorldInfo.func_85131_q(this.worldInfoInstance));
    }

    public Object call()
    {
        return this.callLevelGameModeInfo();
    }
}
