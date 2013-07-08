package org.minetweak.event.block;

import org.minetweak.block.IBlock;

/**
 * Represents a block related event.
 */
public abstract class BlockEvent {
    protected IBlock block;

    public BlockEvent(final IBlock theBlock) {
        block = theBlock;
    }

    /**
     * Gets the block involved in this event.
     *
     * @return The TweakBlock which block is involved in this event
     */
    public final IBlock getBlock() {
        return block;
    }
}