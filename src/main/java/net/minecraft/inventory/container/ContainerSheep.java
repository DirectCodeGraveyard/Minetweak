package net.minecraft.inventory.container;

import net.minecraft.entity.EntityPlayer;
import net.minecraft.entity.EntitySheep;

public class ContainerSheep extends Container {
    final EntitySheep field_90034_a;

    public ContainerSheep(EntitySheep par1EntitySheep) {
        this.field_90034_a = par1EntitySheep;
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return false;
    }
}
