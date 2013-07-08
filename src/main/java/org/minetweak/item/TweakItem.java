package org.minetweak.item;

public class TweakItem {
    private net.minecraft.src.Item item;

    public TweakItem(net.minecraft.src.Item item) {
        this.item = item;
    }

    public net.minecraft.src.Item getItem() {
        return item;
    }
}
