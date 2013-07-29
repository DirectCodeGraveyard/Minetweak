package org.minetweak.event.block;

import net.minecraft.item.ItemStack;
import org.minetweak.block.IBlock;
import org.minetweak.block.IBlockState;
import org.minetweak.entity.Player;
import org.minetweak.event.helper.Cancellable;

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

    public IBlock getBlockPlaced() {
        return getBlock();
    }

    public IBlockState getBlockReplacedState() {
        return this.replacedBlockState;
    }

    public IBlock getBlockAgainst() {
        return placedAgainst;
    }

    public ItemStack getItemInHand() {
        return itemInHand;
    }

    public boolean canBuild() {
        return this.canBuild;
    }

    public void setBuild(boolean canBuild) {
        this.canBuild = canBuild;
    }
}