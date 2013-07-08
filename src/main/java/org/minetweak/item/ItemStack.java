package org.minetweak.item;

public class ItemStack {
    private net.minecraft.src.ItemStack stack;
    private Item item;

    public ItemStack(net.minecraft.src.ItemStack stack) {
        this.stack = stack;
        this.item = new Item(stack.getItem());
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

    public Item getItem() {
        return item;
    }

    public Integer getMaxStackSize() {
        return stack.getMaxStackSize();
    }
}
