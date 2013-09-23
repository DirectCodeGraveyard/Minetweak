package org.minetweak.world;

import net.minecraft.world.gen.world.WorldGenerator;

@SuppressWarnings("UnusedDeclaration")
public class WorldGeneration {
    private static final WorldGeneration instance = new WorldGeneration();

    /**
     * Stub - This will be used to add a WorldGenerator
     *
     * @param generator the world gen to add
     */
    public void addWorldGenerator(WorldGenerator generator) {

    }

    /**
     * Gets the WorldGeneration Instance
     *
     * @return Instance
     */
    public static WorldGeneration getInstance() {
        return instance;
    }
}
