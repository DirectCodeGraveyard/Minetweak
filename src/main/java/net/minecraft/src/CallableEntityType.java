package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEntityType implements Callable<String> {
    final Entity field_85155_a;

    CallableEntityType(Entity par1Entity) {
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
