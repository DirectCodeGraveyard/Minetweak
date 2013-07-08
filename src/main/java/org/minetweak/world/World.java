package org.minetweak.world;

import org.minetweak.block.TweakBlock;

public class World {

    private final net.minecraft.src.WorldServer world;

    public World(net.minecraft.src.WorldServer world) {
        this.world = world;
    }

    public TweakBlock getBlockAt(int x, int y, int z) {
        return getChunkAt(x >> 4, z >> 4).getBlock(x & 0xF, y & 0xFF, z & 0xF);
    }

    public int getBlockTypeIdAt(int x, int y, int z) {
        return world.getBlockId(x, y, z);
    }

    public Chunk getChunkAt(int x, int z) {
        return this.world.theChunkProviderServer.loadChunk(x, z).MineTweakChunk;
    }

    public Chunk getChunkAt(TweakBlock tweakBlock) {
        return getChunkAt(tweakBlock.getX() >> 4, tweakBlock.getZ() >> 4);
    }

    public net.minecraft.src.WorldServer getHandle() {
        return world;
    }

}
