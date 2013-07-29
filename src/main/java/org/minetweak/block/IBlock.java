package org.minetweak.block;

import net.minecraft.block.Block;
import org.minetweak.material.Material;
import org.minetweak.world.Chunk;
import org.minetweak.world.World;

public interface IBlock {

    byte getData();

    int getTypeId();

    World getWorld();

    int getX();

    int getY();

    int getZ();

    void setData(byte data);

    void setData(byte data, boolean applyPhysics);

    Chunk getChunk();

    Material getType();

    boolean setTypeId(int type);

    boolean setTypeId(int type, boolean applyPhysics);

    boolean setTypeIdAndData(int type, byte data, boolean applyPhysics);

    boolean isPowered();

    void setPowered(boolean powered);

    Block getMCBlock();
}