package net.minecraft.src;

public class ClientsideAttributeMap extends BaseAttributeMap {
    public ReadonlyAttributeInstance func_111157_c(Attribute par1Attribute) {
        return (ReadonlyAttributeInstance) super.func_111151_a(par1Attribute);
    }

    public ReadonlyAttributeInstance func_111155_b(String par1Str) {
        return (ReadonlyAttributeInstance) super.func_111152_a(par1Str);
    }

    public ReadonlyAttributeInstance func_111156_d(Attribute par1Attribute) {
        if (this.field_111153_b.containsKey(par1Attribute.func_111108_a())) {
            throw new IllegalArgumentException("Attribute is already registered!");
        } else {
            ReadonlyAttributeInstance var2 = new ReadonlyAttributeInstance(par1Attribute);
            this.field_111153_b.put(par1Attribute.func_111108_a(), var2);
            this.field_111154_a.put(par1Attribute, var2);
            return var2;
        }
    }

    public AttributeInstance func_111150_b(Attribute par1Attribute) {
        return this.func_111156_d(par1Attribute);
    }

    public AttributeInstance func_111152_a(String par1Str) {
        return this.func_111155_b(par1Str);
    }

    public AttributeInstance func_111151_a(Attribute par1Attribute) {
        return this.func_111157_c(par1Attribute);
    }
}
