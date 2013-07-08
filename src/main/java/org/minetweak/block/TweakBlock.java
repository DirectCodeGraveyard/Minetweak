package org.minetweak.block;

import org.minetweak.material.Material;
import org.minetweak.world.Chunk;
import org.minetweak.world.World;

public class TweakBlock implements IBlock {
    private final Chunk chunk;
    private final int x;
    private final int y;
    private final int z;

    public TweakBlock(Chunk chunk, int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.chunk = chunk;
    }

    public World getWorld() {
        return chunk.getWorld();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public void setData(final byte data) {
        chunk.getHandle().worldObj.setBlockMetadata(x, y, z, data, 3);
    }

    public TweakBlockState getState() {
        Material material = getType();
        if (material == null)
            return new TweakBlockState(this);
        switch (material) {
            default:
                return new TweakBlockState(this);
        }
    }

    public Material getType() {
        return Material.getMaterial(getTypeId());
    }

    public void setData(final byte data, boolean applyPhysics) {
        if (applyPhysics) {
            chunk.getHandle().worldObj.setBlockMetadata(x, y, z, data, 3);
        } else {
            chunk.getHandle().worldObj.setBlockMetadata(x, y, z, data, 2);
        }
    }

    public byte getData() {
        return (byte) chunk.getHandle().getBlockMetadata(this.x & 0xF, this.y & 0xFF, this.z & 0xF);
    }

    public boolean setTypeId(final int type) {
        return chunk.getHandle().worldObj.setBlock(x, y, z, type, getData(), 3);
    }

    public boolean setTypeId(final int type, final boolean applyPhysics) {
        if (applyPhysics) {
            return setTypeId(type);
        } else {
            return chunk.getHandle().worldObj.setBlock(x, y, z, type, getData(), 2);
        }
    }

    public boolean setTypeIdAndData(final int type, final byte data, final boolean applyPhysics) {
        if (applyPhysics) {
            return chunk.getHandle().worldObj.setBlock(x, y, z, type, data, 3);
        } else {
            boolean success = chunk.getHandle().worldObj.setBlock(x, y, z, type, data, 2);
            if (success) {
                chunk.getHandle().worldObj.markBlockForUpdate(x, y, z);
            }
            return success;
        }
    }

    public int getTypeId() {
        return chunk.getHandle().getBlockID(this.x & 0xF, this.y & 0xFF, this.z & 0xF);
    }
}