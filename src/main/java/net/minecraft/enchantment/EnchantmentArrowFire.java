package net.minecraft.enchantment;

import net.minecraft.utils.enums.EnumEnchantmentType;

public class EnchantmentArrowFire extends Enchantment {
    public EnchantmentArrowFire(int par1, int par2) {
        super(par1, par2, EnumEnchantmentType.bow);
        this.setName("arrowFire");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    @Override
    public int getMinEnchantability(int par1) {
        return 20;
    }

    /**
     * Returns the maximum value of enchantability needed on the enchantment level passed.
     */
    @Override
    public int getMaxEnchantability(int par1) {
        return 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    @Override
    public int getMaxLevel() {
        return 1;
    }
}
