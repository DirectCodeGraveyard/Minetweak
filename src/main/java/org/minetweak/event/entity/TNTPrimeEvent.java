package org.minetweak.event.entity;

import org.minetweak.entity.EntityTNT;
import org.minetweak.event.helper.Cancellable;

public class TNTPrimeEvent implements EntityEvent, Cancellable {
    private EntityTNT entity;
    private boolean cancelled;

    public TNTPrimeEvent(EntityTNT entity) {
        this.entity = entity;
    }

    public EntityTNT getTNT() {
        return entity;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
