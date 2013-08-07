package org.minetweak.entity;

import org.minetweak.util.Tuple;

/**
 * Class Representing an EntityVillager
 */
public class EntityVillager extends Entity {
    public EntityVillager(net.minecraft.entity.EntityVillager villager) {
        super(villager);
    }

    public static void registerSellItem(int id, Tuple tuple) {
        net.minecraft.entity.EntityVillager.villagersSellingList.put(id, tuple);
    }
}
