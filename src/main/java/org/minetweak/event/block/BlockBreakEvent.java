package org.minetweak.event.block;

import org.minetweak.block.IBlock;
import org.minetweak.entity.Player;
import org.minetweak.event.helper.Cancellable;

public class BlockBreakEvent extends BlockExpEvent implements Cancellable {
    private final Player player;
    private boolean cancel;

    public BlockBreakEvent(final IBlock theBlock, Player username) {
        super(theBlock, 0);

        this.player = username;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}