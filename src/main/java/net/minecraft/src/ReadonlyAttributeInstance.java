package net.minecraft.src;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.UUID;

public class ReadonlyAttributeInstance implements AttributeInstance {
    private final Attribute field_111142_a;
    private double field_111141_b;

    public ReadonlyAttributeInstance(Attribute par1Attribute) {
        this.field_111142_a = par1Attribute;
        this.field_111141_b = par1Attribute.func_111110_b();
    }

    public Attribute func_111123_a() {
        return this.field_111142_a;
    }

    public double func_111125_b() {
        return this.field_111141_b;
    }

    public void func_111128_a(double par1) {
    }

    public Collection func_111122_c() {
        return Sets.newHashSet();
    }

    public AttributeModifier func_111127_a(UUID par1UUID) {
        return null;
    }

    public void func_111121_a(AttributeModifier par1AttributeModifier) {
        throw new UnsupportedOperationException("Cannot modify readonly attribute");
    }

    public void func_111124_b(AttributeModifier par1AttributeModifier) {
        throw new UnsupportedOperationException("Cannot modify readonly attribute");
    }

    public double func_111126_e() {
        return this.field_111141_b;
    }
}
