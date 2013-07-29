package org.minetweak.event.block;

import org.minetweak.block.IBlock;

public abstract class BlockEvent {
    protected IBlock block;

    public BlockEvent(final IBlock theBlock) {
        block = theBlock;
    }

    public final IBlock getBlock() {
        return block;
    }
}