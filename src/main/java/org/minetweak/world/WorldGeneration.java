package org.minetweak.world;

import net.minecraft.world.gen.world.WorldGenerator;

@SuppressWarnings("UnusedDeclaration")
public class WorldGeneration {
    private static WorldGeneration instance = new WorldGeneration();

    /**
     * This is a stub
     *
     * @param generator the world gen to add
     */
    public void addWorldGenerator(WorldGenerator generator) {

    }

    /**
     * Gets the WorldGeneration Instance
     *
     * @return
     */
    public static WorldGeneration getInstance() {
        return instance;
    }
}
