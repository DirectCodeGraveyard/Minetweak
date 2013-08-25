package org.minetweak.world;

import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.EmptyChunk;
import org.minetweak.block.Block;

import java.lang.ref.WeakReference;

public class Chunk {
    private WeakReference<net.minecraft.world.chunk.Chunk> weakChunk;
    private final WorldServer worldServer;
    private final int x;
    private final int z;

    public Chunk(net.minecraft.world.chunk.Chunk chunk) {
        if (!(chunk instanceof EmptyChunk)) {
            this.weakChunk = new WeakReference<net.minecraft.world.chunk.Chunk>(chunk);
        }

        worldServer = (WorldServer) getHandle().worldObj;
        x = getHandle().xPosition;
        z = getHandle().zPosition;
    }

    /**
     * Gets the World the Chunk is in
     *
     * @return world of chunk
     */
    public World getWorld() {
        return worldServer.getWorld();
    }

    /**
     * Gets the MC Chunk
     *
     * @return minecraft chunk
     */
    public net.minecraft.world.chunk.Chunk getHandle() {
        net.minecraft.world.chunk.Chunk c = weakChunk.get();

        if (c == null) {
            c = worldServer.getChunkFromChunkCoords(x, z);

            if (!(c instanceof EmptyChunk)) {
                weakChunk = new WeakReference<net.minecraft.world.chunk.Chunk>(c);
            }
        }

        return c;
    }

    /**
     * X Position
     *
     * @return x position
     */
    public int getX() {
        return x;
    }

    /**
     * Z Position
     *
     * @return z position
     */
    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "Chunk{" + "x=" + getX() + "z=" + getZ() + '}';
    }

    /**
     * Gets a block in a chunk
     *
     * @param x x-position
     * @param y y-position
     * @param z z-position
     * @return block
     */
    public Block getBlock(int x, int y, int z) {
        return new Block(this, (getX() << 4) | (x & 0xF), y & 0xFF, (getZ() << 4) | (z & 0xF));
    }
}