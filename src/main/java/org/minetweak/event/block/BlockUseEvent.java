package org.minetweak.event.block;

import org.minetweak.block.IBlock;
import org.minetweak.entity.Player;
import org.minetweak.event.helper.Cancellable;

public class BlockUseEvent extends BlockEvent implements Cancellable {
    private Player player;
    private boolean cancel;

    public BlockUseEvent(Player player, IBlock theBlock) {
        super(theBlock);
        this.player = player;
        this.cancel = false;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public Player getPlayer() {
        return player;
    }

    public IBlock getBlockUsed() {
        return getBlock();
    }
}
