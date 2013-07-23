package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;

public interface IEnchantmentModifier {
    /**
     * Generic method use to calculate modifiers of offensive or defensive enchantment values.
     */
    public void calculateModifier(Enchantment var1, int var2);
}
