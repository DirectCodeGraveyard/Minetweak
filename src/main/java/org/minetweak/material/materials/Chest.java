package org.minetweak.material.materials;

import org.minetweak.material.Material;
import org.minetweak.event.block.BlockFace;

/**
 * Represents a chest
 */
public class Chest extends DirectionalContainer {

    public Chest() {
        super(Material.CHEST);
    }

    /**
     * Instantiate a chest facing in a particular direction.
     *
     * @param direction the direction the chest's lit opens towards
     */
    public Chest(BlockFace direction) {
        this();
        setFacingDirection(direction);
    }

    public Chest(final int type) {
        super(type);
    }

    public Chest(final Material type) {
        super(type);
    }

    public Chest(final int type, final byte data) {
        super(type, data);
    }

    public Chest(final Material type, final byte data) {
        super(type, data);
    }

    @Override
    public Chest clone() {
        return (Chest) super.clone();
    }
}
