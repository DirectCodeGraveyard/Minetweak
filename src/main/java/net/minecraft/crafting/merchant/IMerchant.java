package net.minecraft.crafting.merchant;

import net.minecraft.entity.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IMerchant {
    void setCustomer(EntityPlayer var1);

    EntityPlayer getCustomer();

    MerchantRecipeList getRecipes(EntityPlayer var1);

    void useRecipe(MerchantRecipe var1);

    void func_110297_a_(ItemStack var1);
}
