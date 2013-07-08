package org.minetweak.event.block;

import org.minetweak.block.Block;

/**
 * An event that's called when a block yields experience.
 */
public class BlockExpEvent extends BlockEvent {
    private int exp;

    public BlockExpEvent(Block block, int exp) {
        super(block);

        this.exp = exp;
    }

    /**
     * Get the experience dropped by the block after the event has processed
     *
     * @return The experience to drop
     */
    public int getExpToDrop() {
        return exp;
    }

    /**
     * Set the amount of experience dropped by the block after the event has processed
     *
     * @param exp 1 or higher to drop experience, else nothing will drop
     */
    public void setExpToDrop(int exp) {
        this.exp = exp;
    }
}