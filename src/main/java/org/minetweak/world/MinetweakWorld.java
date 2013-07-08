package org.minetweak.world;

import org.minetweak.block.Block;

public class MinetweakWorld implements World  {

    private final net.minecraft.src.WorldServer world;

    public MinetweakWorld(net.minecraft.src.WorldServer world) {
        this.world = world;
    }

    public Block getBlockAt(int x, int y, int z) {
        return getChunkAt(x >> 4, z >> 4).getBlock(x & 0xF, y & 0xFF, z & 0xF);
    }

    @Override
    public int getBlockTypeIdAt(int x, int y, int z) {
        return world.getBlockId(x, y, z);
    }

    public Chunk getChunkAt(int x, int z) {
        return this.world.theChunkProviderServer.loadChunk(x, z).MineTweakChunk;
    }

    public Chunk getChunkAt(Block block) {
        return getChunkAt(block.getX() >> 4, block.getZ() >> 4);
    }

    public net.minecraft.src.WorldServer getHandle() {
        return world;
    }

}
