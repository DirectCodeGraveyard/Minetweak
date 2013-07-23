package net.minecraft.entity;

import net.minecraft.item.ItemStack;
import net.minecraft.utils.chat.ChatMessageComponent;
import net.minecraft.src.DamageSource;
import net.minecraft.stats.StatCollector;

public class EntityDamageSource extends DamageSource {
    protected Entity damageSourceEntity;

    public EntityDamageSource(String par1Str, Entity par2Entity) {
        super(par1Str);
        this.damageSourceEntity = par2Entity;
    }

    public Entity getEntity() {
        return this.damageSourceEntity;
    }

    public ChatMessageComponent func_111181_b(EntityLivingBase par1EntityLivingBase) {
        ItemStack var2 = this.damageSourceEntity instanceof EntityLivingBase ? ((EntityLivingBase) this.damageSourceEntity).getHeldItem() : null;
        String var3 = "death.attack." + this.damageType;
        String var4 = var3 + ".item";
        return var2 != null && var2.hasDisplayName() && StatCollector.func_94522_b(var4) ? ChatMessageComponent.func_111082_b(var4, new Object[]{par1EntityLivingBase.getTranslatedEntityName(), this.damageSourceEntity.getTranslatedEntityName(), var2.getDisplayName()}) : ChatMessageComponent.func_111082_b(var3, new Object[]{par1EntityLivingBase.getTranslatedEntityName(), this.damageSourceEntity.getTranslatedEntityName()});
    }

    /**
     * Return whether this damage source will have its damage amount scaled based on the current difficulty.
     */
    public boolean isDifficultyScaled() {
        return this.damageSourceEntity != null && this.damageSourceEntity instanceof EntityLivingBase && !(this.damageSourceEntity instanceof EntityPlayer);
    }
}
