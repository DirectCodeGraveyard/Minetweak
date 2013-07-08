package org.minetweak.block;

import org.minetweak.material.Material;
import org.minetweak.world.Chunk;
import org.minetweak.world.World;

/**
 * Represents a block. This is a live object, and only one Block may exist for
 * any given location in a world. The state of the block may change concurrently
 * to your own handling of it; use block.getState() to get a snapshot state of a
 * block which will not be modified.
 */
public interface IBlock {

    /**
     * Gets the metadata for this block
     *
     * @return block specific metadata
     */
    byte getData();
    /**
     * Gets the type-id of this block
     *
     * @return block type-id
     */
    int getTypeId();

    /**
     * Gets the world which contains this Block
     *
     * @return World containing this block
     */
    World getWorld();

    /**
     * Gets the x-coordinate of this block
     *
     * @return x-coordinate
     */
    int getX();

    /**
     * Gets the y-coordinate of this block
     *
     * @return y-coordinate
     */
    int getY();

    /**
     * Gets the z-coordinate of this block
     *
     * @return z-coordinate
     */
    int getZ();
    /**
     * Sets the metadata for this block
     *
     * @param data New block specific metadata
     */
    void setData(byte data);

    /**
     * Sets the metadata for this block
     *
     * @param data New block specific metadata
     * @param applyPhysics False to cancel physics from the changed block.
     */
    void setData(byte data, boolean applyPhysics);

    /**
     * Gets the chunk which contains this block
     *
     * @return Containing Chunk
     */
    Chunk getChunk();

    /**
     * Gets the type of this block
     *
     * @return block type
     */
    Material getType();

    /**
     * Sets the type-id of this block
     *
     * @param type Type-Id to change this block to
     * @return whether the block was changed
     */
    boolean setTypeId(int type);

    /**
     * Sets the type-id of this block
     *
     * @param type Type-Id to change this block to
     * @param applyPhysics False to cancel physics on the changed block.
     * @return whether the block was changed
     */
    boolean setTypeId(int type, boolean applyPhysics);

    /**
     * Sets the type-id of this block
     *
     * @param type Type-Id to change this block to
     * @param data The data value to change this block to
     * @param applyPhysics False to cancel physics on the changed block
     * @return whether the block was changed
     */
    boolean setTypeIdAndData(int type, byte data, boolean applyPhysics);

}