package org.minetweak.event.entity;

import net.minecraft.entity.EntityLightningBolt;
import net.minecraft.entity.EntityPig;
import net.minecraft.entity.EntityPigZombie;

public class PigZapEvent implements EntityEvent {

    private EntityPig pigEntity;
    private EntityPigZombie pigZombieEntity;
    private EntityLightningBolt boltEntity;

    private boolean cancelled;

    public PigZapEvent(EntityPig pigEntity, EntityPigZombie pigZombieEntity, EntityLightningBolt boltEntity) {
        this.pigEntity = pigEntity;
        this.pigZombieEntity = pigZombieEntity;
        this.boltEntity = boltEntity;
    }

    public EntityPig getEntityPig() {
        return pigEntity;
    }

    public EntityPigZombie getEntityPigZombie() {
        return pigZombieEntity;
    }

    public EntityLightningBolt getBoltEntity() {
        return boltEntity;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        cancelled = false;
    }
}
