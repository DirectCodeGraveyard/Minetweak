package org.minetweak.material.materials;

import org.minetweak.event.block.BlockFace;

/**
 * Indicates that a block can be attached to another block
 */
public interface Attachable extends Directional {

    /**
     * Gets the face that this block is attached on
     *
     * @return BlockFace attached to
     */
    public BlockFace getAttachedFace();
}
