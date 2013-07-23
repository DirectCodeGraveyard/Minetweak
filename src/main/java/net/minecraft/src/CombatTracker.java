package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.utils.MathHelper;
import net.minecraft.utils.chat.ChatMessageComponent;

import java.util.ArrayList;
import java.util.List;

public class CombatTracker {
    private final List<CombatEntry> field_94556_a = new ArrayList<CombatEntry>();
    private final EntityLivingBase field_94554_b;
    private int field_94555_c;
    private boolean field_94552_d;
    private boolean field_94553_e;
    private String field_94551_f;

    public CombatTracker(EntityLivingBase par1EntityLivingBase) {
        this.field_94554_b = par1EntityLivingBase;
    }

    public void func_94545_a() {
        this.func_94542_g();

        if (this.field_94554_b.isOnLadder()) {
            int var1 = this.field_94554_b.worldObj.getBlockId(MathHelper.floor_double(this.field_94554_b.posX), MathHelper.floor_double(this.field_94554_b.boundingBox.minY), MathHelper.floor_double(this.field_94554_b.posZ));

            if (var1 == Block.ladder.blockID) {
                this.field_94551_f = "ladder";
            } else if (var1 == Block.vine.blockID) {
                this.field_94551_f = "vines";
            }
        } else if (this.field_94554_b.isInWater()) {
            this.field_94551_f = "water";
        }
    }

    public void func_94547_a(DamageSource par1DamageSource, float par2, float par3) {
        this.func_94549_h();
        this.func_94545_a();
        CombatEntry var4 = new CombatEntry(par1DamageSource, this.field_94554_b.ticksExisted, par2, par3, this.field_94551_f, this.field_94554_b.fallDistance);
        this.field_94556_a.add(var4);
        this.field_94555_c = this.field_94554_b.ticksExisted;
        this.field_94553_e = true;
        this.field_94552_d |= var4.func_94559_f();
    }

    public ChatMessageComponent func_94546_b() {
        if (this.field_94556_a.size() == 0) {
            return ChatMessageComponent.func_111082_b("death.attack.generic", this.field_94554_b.getTranslatedEntityName());
        } else {
            CombatEntry var1 = this.func_94544_f();
            CombatEntry var2 = this.field_94556_a.get(this.field_94556_a.size() - 1);
            String var4 = var2.func_94558_h();
            Entity var5 = var2.func_94560_a().getEntity();
            ChatMessageComponent var3;

            if (var1 != null && var2.func_94560_a() == DamageSource.fall) {
                String var6 = var1.func_94558_h();

                if (var1.func_94560_a() != DamageSource.fall && var1.func_94560_a() != DamageSource.outOfWorld) {
                    if (var6 != null && (var4 == null || !var6.equals(var4))) {
                        Entity var9 = var1.func_94560_a().getEntity();
                        ItemStack var8 = var9 instanceof EntityLivingBase ? ((EntityLivingBase) var9).getHeldItem() : null;

                        if (var8 != null && var8.hasDisplayName()) {
                            var3 = ChatMessageComponent.func_111082_b("death.fell.assist.item", this.field_94554_b.getTranslatedEntityName(), var6, var8.getDisplayName());
                        } else {
                            var3 = ChatMessageComponent.func_111082_b("death.fell.assist", this.field_94554_b.getTranslatedEntityName(), var6);
                        }
                    } else if (var4 != null) {
                        ItemStack var7 = var5 instanceof EntityLivingBase ? ((EntityLivingBase) var5).getHeldItem() : null;

                        if (var7 != null && var7.hasDisplayName()) {
                            var3 = ChatMessageComponent.func_111082_b("death.fell.finish.item", this.field_94554_b.getTranslatedEntityName(), var4, var7.getDisplayName());
                        } else {
                            var3 = ChatMessageComponent.func_111082_b("death.fell.finish", this.field_94554_b.getTranslatedEntityName(), var4);
                        }
                    } else {
                        var3 = ChatMessageComponent.func_111082_b("death.fell.killer", this.field_94554_b.getTranslatedEntityName());
                    }
                } else {
                    var3 = ChatMessageComponent.func_111082_b("death.fell.accident." + this.func_94548_b(var1), this.field_94554_b.getTranslatedEntityName());
                }
            } else {
                var3 = var2.func_94560_a().func_111181_b(this.field_94554_b);
            }

            return var3;
        }
    }

    public EntityLivingBase func_94550_c() {
        EntityLivingBase var1 = null;
        EntityPlayer var2 = null;
        float var3 = 0.0F;
        float var4 = 0.0F;

        for (CombatEntry var6 : this.field_94556_a) {
            if (var6.func_94560_a().getEntity() instanceof EntityPlayer) {
                var4 = var6.func_94563_c();
                var2 = (EntityPlayer) var6.func_94560_a().getEntity();
            }

            if (var6.func_94560_a().getEntity() instanceof EntityLivingBase) {
                var3 = var6.func_94563_c();
                var1 = (EntityLivingBase) var6.func_94560_a().getEntity();
            }
        }

        if (var2 != null && var4 >= var3 / 3.0F) {
            return var2;
        } else {
            return var1;
        }
    }

    private CombatEntry func_94544_f() {
        CombatEntry var1 = null;
        CombatEntry var2 = null;
        byte var3 = 0;
        float var4 = 0.0F;

        for (int var5 = 0; var5 < this.field_94556_a.size(); ++var5) {
            CombatEntry var6 = this.field_94556_a.get(var5);
            CombatEntry var7 = var5 > 0 ? this.field_94556_a.get(var5 - 1) : null;

            if ((var6.func_94560_a() == DamageSource.fall || var6.func_94560_a() == DamageSource.outOfWorld) && var6.func_94561_i() > 0.0F && (var1 == null || var6.func_94561_i() > var4)) {
                if (var5 > 0) {
                    var1 = var7;
                } else {
                    var1 = var6;
                }

                var4 = var6.func_94561_i();
            }

            if (var6.func_94562_g() != null && (var2 == null || var6.func_94563_c() > (float) var3)) {
                var2 = var6;
            }
        }

        if (var4 > 5.0F && var1 != null) {
            return var1;
        } else if (var3 > 5 && var2 != null) {
            return var2;
        } else {
            return null;
        }
    }

    private String func_94548_b(CombatEntry par1CombatEntry) {
        return par1CombatEntry.func_94562_g() == null ? "generic" : par1CombatEntry.func_94562_g();
    }

    private void func_94542_g() {
        this.field_94551_f = null;
    }

    private void func_94549_h() {
        int var1 = this.field_94552_d ? 300 : 100;

        if (this.field_94553_e && this.field_94554_b.ticksExisted - this.field_94555_c > var1) {
            this.field_94556_a.clear();
            this.field_94553_e = false;
            this.field_94552_d = false;
        }
    }
}
