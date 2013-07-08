package org.minetweak.event.block;

import net.minecraft.src.ItemStack;
import org.minetweak.block.IBlock;
import org.minetweak.block.IBlockState;
import org.minetweak.entity.Player;
import org.minetweak.event.helper.Cancellable;

/**
 * Called when a block is placed by a player.
 * <p>
 * If a TweakBlock Place event is cancelled, the block will not be placed.
 */
public class BlockPlaceEvent extends BlockEvent implements Cancellable {
    protected boolean cancel;
    protected boolean canBuild;
    protected IBlock placedAgainst;
    protected IBlockState replacedBlockState;
    protected ItemStack itemInHand;
    protected Player player;

    public BlockPlaceEvent(final IBlock placedBlock, final IBlockState replacedBlockState, final IBlock placedAgainst, final ItemStack itemInHand, final Player thePlayer, final boolean canBuild) {
        super(placedBlock);
        this.placedAgainst = placedAgainst;
        this.itemInHand = itemInHand;
        this.player = thePlayer;
        this.replacedBlockState = replacedBlockState;
        this.canBuild = canBuild;
        cancel = false;
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
        return player;
    }

    /**
     * Clarity method for getting the placed block. Not really needed
     * except for reasons of clarity.
     *
     * @return The TweakBlock that was placed
     */
    public IBlock getBlockPlaced() {
        return getBlock();
    }

    /**
     * Gets the TweakBlockState for the block which was replaced. Material type air mostly.
     *
     * @return The TweakBlockState for the block which was replaced.
     */
    public IBlockState getBlockReplacedState() {
        return this.replacedBlockState;
    }

    /**
     * Gets the block that this block was placed against
     *
     * @return TweakBlock the block that the new block was placed against
     */
    public IBlock getBlockAgainst() {
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