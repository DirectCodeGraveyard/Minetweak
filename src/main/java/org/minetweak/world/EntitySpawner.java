package org.minetweak.world;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.utils.MathHelper;
import org.minetweak.entity.Entity;

public class EntitySpawner {
    public static Entity spawnCreature(World world, int par1, double par2, double par4, double par6) {
        if (!EntityList.entityEggs.containsKey(par1)) {
            return null;
        } else {
            net.minecraft.entity.Entity var8 = null;

            for (int var9 = 0; var9 < 1; ++var9) {
                var8 = EntityList.createEntityByID(par1, world.getWorldServer());

                if (var8 != null && var8 instanceof EntityLivingBase) {
                    EntityLiving var10 = (EntityLiving) var8;
                    var8.setLocationAndAngles(par2, par4, par6, MathHelper.wrapAngleTo180_float(world.getWorldServer().rand.nextFloat() * 360.0F), 0.0F);
                    var10.rotationYawHead = var10.rotationYaw;
                    var10.renderYawOffset = var10.rotationYaw;
                    var10.func_110161_a(null);
                    world.getWorldServer().spawnEntityInWorld(var8);
                    var10.playLivingSound();
                }
            }

            return new Entity(var8);
        }
    }
}
