package org.minetweak.event.block;

import org.minetweak.entity.Player;
import org.minetweak.event.helper.Cancellable;

public class BlockUseEvent extends BlockEvent implements Cancellable {
    private Player player;
    private boolean cancel;

    public BlockUseEvent(Player player, Block theBlock) {
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

    /**
     * Gets the player who placed the block involved in this event.
     * @return The Player who placed the block involved in this event
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Clarity method for getting the placed block. Not really needed
     * except for reasons of clarity.
     * @return The Block that was placed
     */
    public Block getBlockUsed() {
        return getBlock();
    }
}
