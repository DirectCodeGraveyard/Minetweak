package org.minetweak.item;

public class Item {
    private net.minecraft.src.Item item;

    public Item(net.minecraft.src.Item item) {
        this.item = item;
    }

    public net.minecraft.src.Item getItem() {
        return item;
    }
}
