package org.minetweak.world;

import org.minetweak.block.TweakBlock;

import java.lang.ref.WeakReference;

public class Chunk {
    private WeakReference<net.minecraft.src.Chunk> weakChunk;
    private final net.minecraft.src.WorldServer worldServer;
    private final int x;
    private final int z;
    private static final byte[] emptyData = new byte[2048];
    private static final short[] emptyBlockIDs = new short[4096];
    private static final byte[] emptySkyLight = new byte[2048];

    public Chunk(net.minecraft.src.Chunk chunk) {
        if (!(chunk instanceof net.minecraft.src.EmptyChunk)) {
            this.weakChunk = new WeakReference<net.minecraft.src.Chunk>(chunk);
        }

        worldServer = (net.minecraft.src.WorldServer) getHandle().worldObj;
        x = getHandle().xPosition;
        z = getHandle().zPosition;
    }

    public World getWorld() {
        return worldServer.getWorld();
    }

    public World getCraftWorld() {
        return getWorld();
    }

    public net.minecraft.src.Chunk getHandle() {
        net.minecraft.src.Chunk c = weakChunk.get();

        if (c == null) {
            c = worldServer.getChunkFromChunkCoords(x, z);

            if (!(c instanceof net.minecraft.src.EmptyChunk)) {
                weakChunk = new WeakReference<net.minecraft.src.Chunk>(c);
            }
        }

        return c;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "Chunk{" + "x=" + getX() + "z=" + getZ() + '}';
    }

    public TweakBlock getBlock(int x, int y, int z) {
        return new TweakBlock(this, (getX() << 4) | (x & 0xF), y & 0xFF, (getZ() << 4) | (z & 0xF));
    }
}