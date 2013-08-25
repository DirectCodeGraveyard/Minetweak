package org.minetweak.event.block;

import org.minetweak.block.IBlock;
import org.minetweak.entity.Player;
import org.minetweak.event.helper.Cancellable;

/**
 * Called when a block is ignited. If you want to catch when a Player places fire, you need to use {@link BlockPlaceEvent}.
 * If a Block Ignite event is cancelled, the block will not be ignited.
 */
public class BlockIgniteEvent extends BlockEvent implements Cancellable {
    private final IgniteCause cause;
    private final IBlock ignitingBlock;
    private boolean cancel;
    private final Player ignitingEntity;

    public BlockIgniteEvent(final IBlock theBlock, final IgniteCause cause, final Player ignitingEntity) {
        this(theBlock, cause, ignitingEntity, null);
    }

    public BlockIgniteEvent(final IBlock theBlock, final IgniteCause cause, final IBlock ignitingBlock) {
        this(theBlock, cause, null, ignitingBlock);
    }

    public BlockIgniteEvent(final IBlock theBlock, final IgniteCause cause, final Player ignitingEntity, final IBlock ignitingBlock) {
        super(theBlock);
        this.cause = cause;
        this.ignitingEntity = ignitingEntity;
        this.ignitingBlock = ignitingBlock;
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
     * Gets the cause of block ignite.
     *
     * @return An IgniteCause value detailing the cause of block ignition
     */
    public IgniteCause getCause() {
        return cause;
    }

    /**
     * Gets the player who ignited this block
     *
     * @return The Player that placed/ignited the fire block, or null if not ignited by a Player.
     */
    public Player getPlayer() {

        return ignitingEntity;
    }

    /**
     * Gets the block who ignited this block
     *
     * @return The Block that placed/ignited the fire block, or null if not ignited by a Block.
     */
    public IBlock getIgnitingBlock() {
        return ignitingBlock;
    }

    /**
     * An enum to specify the cause of the ignite
     */
    public enum IgniteCause {

        /**
         * Block ignition caused by lava.
         */
        LAVA,
        /**
         * Block ignition caused by a player or dispenser using flint-and-steel.
         */
        FLINT_AND_STEEL,
        /**
         * Block ignition caused by dynamic spreading of fire.
         */
        SPREAD,
        /**
         * Block ignition caused by lightning.
         */
        LIGHTNING,
        /**
         * Block ignition caused by an entity using a fireball.
         */
        FIREBALL,
        /**
         * Block ignition caused by an Ender Crystal.
         */
        ENDER_CRYSTAL,
        /**
         * Block ignition caused by explosion.
         */
        EXPLOSION,
    }
}
