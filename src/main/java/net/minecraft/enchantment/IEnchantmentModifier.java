package net.minecraft.enchantment;

public interface IEnchantmentModifier {
    /**
     * Generic method use to calculate modifiers of offensive or defensive enchantment values.
     */
    public void calculateModifier(Enchantment var1, int var2);
}
