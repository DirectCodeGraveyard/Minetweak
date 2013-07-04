package org.minetweak.event.block;

import net.minecraft.src.ItemStack;
import org.minetweak.Minetweak;
import org.minetweak.entity.Player;

/**
 * Called when a block is placed by a player.
 * <p>
 * If a Block Place event is cancelled, the block will not be placed.
 */
public class BlockPlaceEvent extends BlockEvent {
    protected boolean cancel;
    protected boolean canBuild;
    protected Block placedAgainst;
    protected ItemStack itemInHand;
    protected String player;

    public BlockPlaceEvent(final Block placedBlock, final Block placedAgainst, final ItemStack itemInHand, final String thePlayer, final boolean canBuild) {
        super(placedBlock);
        this.placedAgainst = placedAgainst;
        this.itemInHand = itemInHand;
        this.player = thePlayer;
        this.canBuild = canBuild;
    }

    public boolean isCancelled() {
        return cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    /**
     * Gets the player who placed the block involved in this event.
     *
     * @return The Player who placed the block involved in this event
     */
    public Player getPlayer() {
        return Minetweak.getPlayerByName(player);
    }

    /**
     * Clarity method for getting the placed block. Not really needed
     * except for reasons of clarity.
     *
     * @return The Block that was placed
     */
    public Block getBlockPlaced() {
        return getBlock();
    }

    /**
     * Gets the block that this block was placed against
     *
     * @return Block the block that the new block was placed against
     */
    public Block getBlockAgainst() {
        return placedAgainst;
    }

    /**
     * Gets the item in the player's hand when they placed the block.
     *
     * @return The ItemStack for the item in the player's hand when they placed the block
     */
    public ItemStack getItemInHand() {
        return itemInHand;
    }

    /**
     * Gets the value whether the player would be allowed to build here.
     * Defaults to spawn if the server was going to stop them (such as, the
     * player is in Spawn). Note that this is an entirely different check
     * than BLOCK_CANBUILD, as this refers to a player, not universe-physics
     * rule like cactus on dirt.
     *
     * @return boolean whether the server would allow a player to build here
     */
    public boolean canBuild() {
        return this.canBuild;
    }

    /**
     * Sets the canBuild state of this event. Set to true if you want the
     * player to be able to build.
     *
     * @param canBuild true if you want the player to be able to build
     */
    public void setBuild(boolean canBuild) {
        this.canBuild = canBuild;
    }
}