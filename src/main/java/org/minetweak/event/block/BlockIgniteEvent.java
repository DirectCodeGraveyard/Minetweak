package org.minetweak.event.block;

import org.minetweak.block.IBlock;
import org.minetweak.entity.Player;
import org.minetweak.event.helper.Cancellable;

/**
 * Called when a block is ignited. If you want to catch when a Player places fire, you need to use {@link BlockPlaceEvent}.
 * <p>
 * If a TweakBlock Ignite event is cancelled, the block will not be ignited.
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

    public boolean isCancelled() {
        return cancel;
    }

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
     * @return The TweakBlock that placed/ignited the fire block, or null if not ignited by a TweakBlock.
     */
    public IBlock getIgnitingBlock() {
        return ignitingBlock;
    }

    /**
     * An enum to specify the cause of the ignite
     */
    public enum IgniteCause {

        /**
         * TweakBlock ignition caused by lava.
         */
        LAVA,
        /**
         * TweakBlock ignition caused by a player or dispenser using flint-and-steel.
         */
        FLINT_AND_STEEL,
        /**
         * TweakBlock ignition caused by dynamic spreading of fire.
         */
        SPREAD,
        /**
         * TweakBlock ignition caused by lightning.
         */
        LIGHTNING,
        /**
         * TweakBlock ignition caused by an entity using a fireball.
         */
        FIREBALL,
        /**
         * TweakBlock ignition caused by an Ender Crystal.
         */
        ENDER_CRYSTAL,
        /**
         * TweakBlock ignition caused by explosion.
         */
        EXPLOSION,
    }
}
