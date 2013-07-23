package org.minetweak.world;

import net.minecraft.world.WorldServer;
import org.minetweak.block.TweakBlock;

public class World {

    private final WorldServer world;

    public World(WorldServer world) {
        this.world = world;
    }

    public TweakBlock getBlockAt(int x, int y, int z) {
        return getChunkAt(x >> 4, z >> 4).getBlock(x & 0xF, y & 0xFF, z & 0xF);
    }

    /**
     * Gets the Block ID of the block at a position
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @return block id of position of block
     */
    public int getBlockTypeIdAt(int x, int y, int z) {
        return world.getBlockId(x, y, z);
    }

    /**
     * Gets a Chunk at the specified Location
     * @param x X Position
     * @param z Z Position
     * @return Chunk at position
     */
    public Chunk getChunkAt(int x, int z) {
        return this.world.theChunkProviderServer.loadChunk(x, z).MineTweakChunk;
    }

    /**
     * Gets the Chunk  a block is in
     * @param tweakBlock the block
     * @return the chunk
     */
    public Chunk getChunkAt(TweakBlock tweakBlock) {
        return getChunkAt(tweakBlock.getX() >> 4, tweakBlock.getZ() >> 4);
    }

    /**
     * Gets the WorldServer
     * @return WorldServer instance for world
     */
    public WorldServer getWorldServer() {
        return world;
    }

    /**
     * Can peaceful mobs spawn in this world?
     * @return True if peaceful mobs can spawn
     */
    public boolean canPeacefulMobsSpawn() {
        return getWorldServer().spawnPeacefulMobs();
    }

    /**
     * Can hostile mobs spawn in this world?
     * @return True if hostile mobs can spawn
     */
    public boolean canHostileMobsSpawn() {
        return getWorldServer().spawnHostileMobs();
    }

    /**
     * Set the ability of mob spawns by type, peaceful or hostile.
     * @param peacefulSpawnAbility Peaceful mobs spawn ability
     * @param hostileSpawnAbility Hostile mobs spawn ability
     */
    public void setMobTypeSpawnAbility(boolean peacefulSpawnAbility, boolean hostileSpawnAbility) {
        getWorldServer().setAllowedSpawnTypes(hostileSpawnAbility, peacefulSpawnAbility);
    }
}
