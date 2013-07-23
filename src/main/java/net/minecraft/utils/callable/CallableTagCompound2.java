package net.minecraft.utils.callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.concurrent.Callable;

public class CallableTagCompound2 implements Callable<String> {
    final int field_82588_a;

    final NBTTagCompound theNBTTagCompound;

    public CallableTagCompound2(NBTTagCompound par1NBTTagCompound, int par2) {
        this.theNBTTagCompound = par1NBTTagCompound;
        this.field_82588_a = par2;
    }

    public String func_82586_a() {
        return NBTBase.NBTTypes[this.field_82588_a];
    }

    @Override
    public String call() {
        return this.func_82586_a();
    }
}
