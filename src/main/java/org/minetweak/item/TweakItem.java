package org.minetweak.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TweakItem {
    private Item item;

    public TweakItem(Item item) {
        this.item = item;
    }

    /**
     * Gets the MC Item
     * @return MC Item
     */
    public Item getItem() {
        return item;
    }

    public String getDisplayName(ItemStack stack) {
        return item.getItemDisplayName(stack.getItemStack());
    }

    public int getMaxStackSize() {
        return item.getItemStackLimit();
    }

    public CreativeTabs getCreativeTab() {
        return item.getCreativeTab();
    }

    public static TweakItem getItemById(int id) {
        return new TweakItem(Item.itemsList[id]);
    }
}
