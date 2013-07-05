package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelWeather implements Callable<String> {
    final WorldInfo worldInfoInstance;

    CallableLevelWeather(WorldInfo par1WorldInfo) {
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
