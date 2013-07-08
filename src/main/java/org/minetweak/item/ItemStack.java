package org.minetweak.item;

public class ItemStack {
    private net.minecraft.src.ItemStack stack;
    private TweakItem tweakItem;

    public ItemStack(net.minecraft.src.ItemStack stack) {
        this.stack = stack;
        this.tweakItem = new TweakItem(stack.getItem());
    }

    public net.minecraft.src.ItemStack getItemStack() {
        return stack;
    }

    public String getDisplayName() {
        return stack.getDisplayName();
    }

    public Integer getSize() {
        return stack.stackSize;
    }

    public TweakItem getTweakItem() {
        return tweakItem;
    }

    public Integer getMaxStackSize() {
        return stack.getMaxStackSize();
    }
}
