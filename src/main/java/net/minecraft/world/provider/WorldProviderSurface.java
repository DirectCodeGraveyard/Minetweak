package net.minecraft.world.provider;

public class WorldProviderSurface extends WorldProvider {
    /**
     * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
     */
    @Override
    public String getDimensionName() {
        return "Overworld";
    }
}
