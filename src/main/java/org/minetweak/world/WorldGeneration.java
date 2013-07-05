package org.minetweak.world;

import net.minecraft.src.WorldGenerator;

@SuppressWarnings("UnusedDeclaration")
public class WorldGeneration {
    private static WorldGeneration instance = new WorldGeneration();

    /**
     * This is a stub
     * @param generator the world gen to add
     */
    public static void addWorldGenerator(WorldGenerator generator) {

    }

    public static WorldGeneration getInstance() {
        return instance;
    }
}
