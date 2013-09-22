package net.minecraft.enchantment;

import net.minecraft.utils.enums.EnumEnchantmentType;

public class EnchantmentArrowDamage extends Enchantment {
    public EnchantmentArrowDamage(int par1, int par2) {
        super(par1, par2, EnumEnchantmentType.bow);
        this.setName("arrowDamage");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    @Override
    public int getMinEnchantability(int par1) {
        return 1 + (par1 - 1) * 10;
    }

    /**
     * Returns the maximum value of enchantability needed on the enchantment level passed.
     */
    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 15;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    @Override
    public int getMaxLevel() {
        return 5;
    }
}
