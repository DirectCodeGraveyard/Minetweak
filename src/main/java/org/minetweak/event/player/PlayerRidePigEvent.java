package org.minetweak.event.player;

import net.minecraft.entity.EntityPig;
import org.minetweak.entity.Player;
import org.minetweak.event.helper.Cancellable;

public class PlayerRidePigEvent extends PlayerEvent implements Cancellable {

    private EntityPig pig;

    private boolean cancelled;

    /**
     * Creates a Player Event
     *
     * @param player player involved
     */
    public PlayerRidePigEvent(Player player, EntityPig pig) {
        super(player);
        this.pig = pig;
    }

    public EntityPig getPig() {
        return pig;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
