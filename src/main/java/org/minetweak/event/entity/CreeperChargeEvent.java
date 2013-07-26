package org.minetweak.event.entity;

import org.minetweak.entity.Entity;
import org.minetweak.event.helper.Cancellable;

public class CreeperChargeEvent implements EntityEvent, Cancellable {

    private boolean cancelled;
    private Entity creeper;
    private Entity bolt;

    public CreeperChargeEvent(Entity creeper, Entity bolt) {
        this.creeper = creeper;
        this.bolt = bolt;
    }

    public Entity getEntity() {
        return creeper;
    }

    public Entity getBolt() {
        return bolt;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

}
