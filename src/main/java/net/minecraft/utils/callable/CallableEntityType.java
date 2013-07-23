package net.minecraft.utils.callable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

import java.util.concurrent.Callable;

public class CallableEntityType implements Callable<String> {
    final Entity field_85155_a;

    public CallableEntityType(Entity par1Entity) {
        this.field_85155_a = par1Entity;
    }

    public String callEntityType() {
        return EntityList.getEntityString(this.field_85155_a) + " (" + this.field_85155_a.getClass().getCanonicalName() + ")";
    }

    @Override
    public String call() {
        return this.callEntityType();
    }
}
