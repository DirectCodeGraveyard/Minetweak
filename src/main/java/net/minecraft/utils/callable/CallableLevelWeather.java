package net.minecraft.utils.callable;

import net.minecraft.world.WorldInfo;

import java.util.concurrent.Callable;

public class CallableLevelWeather implements Callable<String> {
    final WorldInfo worldInfoInstance;

    public CallableLevelWeather(WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }

    public String callLevelWeatherInfo() {
        return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", WorldInfo.getRainTime(this.worldInfoInstance), WorldInfo.getRaining(this.worldInfoInstance), WorldInfo.getThunderTime(this.worldInfoInstance), WorldInfo.getThundering(this.worldInfoInstance));
    }

    @Override
    public String call() {
        return this.callLevelWeatherInfo();
    }
}
