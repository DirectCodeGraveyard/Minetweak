package net.minecraft.tileentity;

public class TileEntityDropper extends TileEntityDispenser {
    /**
     * Returns the name of the inventory.
     */
    @Override
    public String getInvName() {
        return this.isInvNameLocalized() ? this.field_94050_c : "container.dropper";
    }
}
