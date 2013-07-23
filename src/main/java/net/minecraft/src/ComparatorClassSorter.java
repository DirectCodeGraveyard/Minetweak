package net.minecraft.src;

import net.minecraft.utils.callable.CallableSuspiciousClasses;

import java.util.Comparator;

public class ComparatorClassSorter implements Comparator<Class> {
    final CallableSuspiciousClasses theSuspiciousClasses;

    public ComparatorClassSorter(CallableSuspiciousClasses par1CallableSuspiciousClasses) {
        this.theSuspiciousClasses = par1CallableSuspiciousClasses;
    }

    public int func_85081_a(Class par1Class, Class par2Class) {
        String var3 = par1Class.getPackage() == null ? "" : par1Class.getPackage().getName();
        String var4 = par2Class.getPackage() == null ? "" : par2Class.getPackage().getName();
        return var3.compareTo(var4);
    }

    @Override
    public int compare(Class par1Obj, Class par2Obj) {
        return this.func_85081_a(par1Obj, par2Obj);
    }
}
