package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.attribute.BaseAttributeMap;

public class PotionAbsorption extends Potion {
    protected PotionAbsorption(int par1, boolean par2, int par3) {
        super(par1, par2, par3);
    }

    public void func_111187_a(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3) {
        par1EntityLivingBase.func_110149_m(par1EntityLivingBase.func_110139_bj() - (float) (4 * (par3 + 1)));
        super.func_111187_a(par1EntityLivingBase, par2BaseAttributeMap, par3);
    }

    public void func_111185_a(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3) {
        par1EntityLivingBase.func_110149_m(par1EntityLivingBase.func_110139_bj() + (float) (4 * (par3 + 1)));
        super.func_111185_a(par1EntityLivingBase, par2BaseAttributeMap, par3);
    }
}
