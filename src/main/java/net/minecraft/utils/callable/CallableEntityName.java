package net.minecraft.utils.callable;

import net.minecraft.entity.Entity;

import java.util.concurrent.Callable;

public class CallableEntityName implements Callable<String> {
    final Entity theEntity;

    public CallableEntityName(Entity par1Entity) {
        this.theEntity = par1Entity;
    }

    public String callEntityName() {
        return this.theEntity.getEntityName();
    }

    @Override
    public String call() {
        return this.callEntityName();
    }
}
