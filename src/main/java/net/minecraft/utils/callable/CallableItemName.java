package net.minecraft.utils.callable;

import net.minecraft.inventory.InventoryPlayer;
import net.minecraft.item.ItemStack;

import java.util.concurrent.Callable;

public class CallableItemName implements Callable<String> {
    final ItemStack theItemStack;

    final InventoryPlayer playerInventory;

    public CallableItemName(InventoryPlayer par1InventoryPlayer, ItemStack par2ItemStack) {
        this.playerInventory = par1InventoryPlayer;
        this.theItemStack = par2ItemStack;
    }

    public String callItemDisplayName() {
        return this.theItemStack.getDisplayName();
    }

    @Override
    public String call() {
        return this.callItemDisplayName();
    }
}
