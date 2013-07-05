package org.minetweak.event.block;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import org.minetweak.Minetweak;
import org.minetweak.entity.Player;

public class BlockPlacedEvent {
    private Block block;
    private int x;
    private int y;
    private int z;
    private Player player;

    public BlockPlacedEvent(Block block, EntityPlayer entityPlayer, int x, int y, int z) {
        this.block = block;
        this.player = Minetweak.getPlayerByName(entityPlayer.getEntityName());
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Player getPlayer() {
        return player;
    }

    public int getZ() {
        return z;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Block getBlock() {
        return block;
    }
}
