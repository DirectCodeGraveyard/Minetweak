package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTameable;

public class EntityAIOwnerHurtTarget extends EntityAITarget {
    EntityTameable theEntityTameable;
    EntityLivingBase theTarget;

    public EntityAIOwnerHurtTarget(EntityTameable par1EntityTameable) {
        super(par1EntityTameable, false);
        this.theEntityTameable = par1EntityTameable;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (!this.theEntityTameable.isTamed()) {
            return false;
        } else {
            EntityLivingBase var1 = this.theEntityTameable.func_130012_q();

            if (var1 == null) {
                return false;
            } else {
                this.theTarget = var1.func_110144_aD();
                return this.isSuitableTarget(this.theTarget, false);
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theTarget);
        super.startExecuting();
    }
}
