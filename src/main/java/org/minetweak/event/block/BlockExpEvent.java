package org.minetweak.event.block;

import org.minetweak.block.IBlock;

public class BlockExpEvent extends BlockEvent {
    private int exp;

    public BlockExpEvent(IBlock block, int exp) {
        super(block);
        this.exp = exp;
    }

    public int getExpToDrop() {
        return exp;
    }

    public void setExpToDrop(int exp) {
        this.exp = exp;
    }
}