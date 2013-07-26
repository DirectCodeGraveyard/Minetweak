package org.minetweak.event.entity;

import org.minetweak.entity.Entity;

public class CreeperChargeEvent implements EntityEvent {

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

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public boolean isCancelled() {
        return cancelled;
    }

}
