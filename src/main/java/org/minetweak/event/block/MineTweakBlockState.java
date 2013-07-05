package org.minetweak.event.block;

import org.minetweak.material.Material;
import org.minetweak.material.materials.MaterialData;
import org.minetweak.world.Chunk;
import org.minetweak.world.MineTweakChunk;
import org.minetweak.world.MineTweakWorld;
import org.minetweak.world.World;

public class MineTweakBlockState implements BlockState {
    private final MineTweakWorld world;
    private final MineTweakChunk chunk;
    private final int x;
    private final int y;
    private final int z;
    protected int type;
    protected MaterialData data;

    public MineTweakBlockState(final Block block) {
        this.world = (MineTweakWorld) block.getWorld();
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.type = block.getTypeId();
        this.chunk = (MineTweakChunk) block.getChunk();

        createData(block.getData());
    }

    public static MineTweakBlockState getBlockState(net.minecraft.src.World world, int x, int y, int z) {
        return new MineTweakBlockState(world.getWorld().getBlockAt(x, y, z));
    }

    public World getWorld() {
        return world;
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

    public void setData(final MaterialData data) {
        Material mat = getType();

        if ((mat == null) || (mat.getData() == null)) {
            this.data = data;
        } else {
            if ((data.getClass() == mat.getData()) || (data.getClass() == MaterialData.class)) {
                this.data = data;
            } else {
                throw new IllegalArgumentException("Provided data is not of type "
                        + mat.getData().getName() + ", found " + data.getClass().getName());
            }
        }
    }

    public MaterialData getData() {
        return data;
    }

    public void setType(final Material type) {
        setTypeId(type.getId());
    }

    public boolean setTypeId(final int type) {
        if (this.type != type) {
            this.type = type;

            createData((byte) 0);
        }
        return true;
    }

    public Material getType() {
        return Material.getMaterial(getTypeId());
    }

    public int getTypeId() {
        return type;
    }

    public Block getBlock() {
        return world.getBlockAt(x, y, z);
    }

    public boolean update() {
        return update(false);
    }

    public boolean update(boolean force) {
        return update(force, true);
    }

    public boolean update(boolean force, boolean applyPhysics) {
        Block block = getBlock();

        if (block.getType() != getType()) {
            if (force) {
                block.setTypeId(getTypeId(), applyPhysics);
            } else {
                return false;
            }
        }

        block.setData(getRawData(), applyPhysics);
        world.getHandle().markBlockForUpdate(x, y, z);

        return true;
    }

    private void createData(final byte data) {
        Material mat = getType();
        if (mat == null || mat.getData() == null) {
            this.data = new MaterialData(type, data);
        } else {
            this.data = mat.getNewData(data);
        }
    }

    public byte getRawData() {
        return data.getData();
    }

    public void setRawData(byte data) {
        this.data.setData(data);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MineTweakBlockState other = (MineTweakBlockState) obj;
        if (this.world != other.world && (this.world == null || !this.world.equals(other.world))) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.z != other.z) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (this.data != other.data && (this.data == null || !this.data.equals(other.data))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.world != null ? this.world.hashCode() : 0);
        hash = 73 * hash + this.x;
        hash = 73 * hash + this.y;
        hash = 73 * hash + this.z;
        hash = 73 * hash + this.type;
        hash = 73 * hash + (this.data != null ? this.data.hashCode() : 0);
        return hash;
    }
}

