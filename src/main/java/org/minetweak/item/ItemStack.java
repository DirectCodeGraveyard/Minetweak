package org.minetweak.item;

import net.minecraft.enchantment.Enchantment;

public class ItemStack {
    private net.minecraft.item.ItemStack stack;
    private TweakItem tweakItem;

    public ItemStack(net.minecraft.item.ItemStack stack) {
        this.stack = stack;
        this.tweakItem = new TweakItem(stack.getItem());
    }

    /**
     * Gets the MC Style ItemStack
     *
     * @return ItemStack from MC
     */
    public net.minecraft.item.ItemStack getItemStack() {
        return stack;
    }

    /**
     * Gets the Item Display Name
     *
     * @return item's display name
     */
    public String getDisplayName() {
        return stack.getDisplayName();
    }

    /**
     * Gets stack size
     *
     * @return stack size
     */
    public int getSize() {
        return stack.stackSize;
    }

    /**
     * Gets the Minetweak Item
     *
     * @return item in stack
     */
    public TweakItem getTweakItem() {
        return tweakItem;
    }

    /**
     * Gets the max stack size
     *
     * @return max stack size
     */
    public int getMaxStackSize() {
        return stack.getMaxStackSize();
    }

    /**
     * Adds the Enchantment to the Stack
     *
     * @param enchantment the enchantment
     * @param level       level of enchantment
     */
    public void addEnchantment(Enchantment enchantment, Integer level) {
        stack.addEnchantment(enchantment, level);
    }
}
