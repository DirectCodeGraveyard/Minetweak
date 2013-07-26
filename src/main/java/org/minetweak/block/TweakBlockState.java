package org.minetweak.block;

import org.minetweak.material.Material;
import org.minetweak.material.MaterialData;
import org.minetweak.world.Chunk;
import org.minetweak.world.World;

public class TweakBlockState implements IBlockState {
    private final World world;
    private final Chunk chunk;
    private final int x;
    private final int y;
    private final int z;
    protected int type;
    protected MaterialData data;

    public TweakBlockState(final TweakBlock tweakBlock) {
        this.world = tweakBlock.getWorld();
        this.x = tweakBlock.getX();
        this.y = tweakBlock.getY();
        this.z = tweakBlock.getZ();
        this.type = tweakBlock.getTypeId();
        this.chunk = tweakBlock.getChunk();

        createData(tweakBlock.getData());
    }

    public static TweakBlockState getBlockState(net.minecraft.world.World world, int x, int y, int z) {
        return new TweakBlockState(world.getWorld().getBlockAt(x, y, z));
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
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

    @Override
    public void setType(final Material type) {
        setTypeId(type.getId());
    }

    @Override
    public boolean setTypeId(final int type) {
        if (this.type != type) {
            this.type = type;

            createData((byte) 0);
        }
        return true;
    }

    @Override
    public Material getType() {
        return Material.getMaterial(getTypeId());
    }

    @Override
    public int getTypeId() {
        return type;
    }

    @Override
    public TweakBlock getBlock() {
        return world.getBlockAt(x, y, z);
    }

    @Override
    public boolean update() {
        return update(false);
    }

    @Override
    public boolean update(boolean force) {
        return update(force, true);
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        TweakBlock tweakBlock = getBlock();

        if (tweakBlock.getType() != getType()) {
            if (force) {
                tweakBlock.setTypeId(getTypeId(), applyPhysics);
            } else {
                return false;
            }
        }

        tweakBlock.setData(getRawData(), applyPhysics);
        world.getWorldServer().markBlockForUpdate(x, y, z);

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

    @Override
    public byte getRawData() {
        return data.getData();
    }

    @Override
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
        final TweakBlockState other = (TweakBlockState) obj;
        return !(this.world != other.world && (this.world == null || !this.world.equals(other.world))) && this.x == other.x && this.y == other.y && this.z == other.z && this.type == other.type && !(this.data != other.data && (this.data == null || !this.data.equals(other.data)));
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

