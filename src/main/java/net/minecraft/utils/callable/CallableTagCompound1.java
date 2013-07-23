package net.minecraft.utils.callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.concurrent.Callable;

public class CallableTagCompound1 implements Callable<String> {
    final String field_82585_a;

    final NBTTagCompound theNBTTagCompound;

    public CallableTagCompound1(NBTTagCompound par1NBTTagCompound, String par2Str) {
        this.theNBTTagCompound = par1NBTTagCompound;
        this.field_82585_a = par2Str;
    }

    public String func_82583_a() {
        return NBTBase.NBTTypes[((NBTBase) NBTTagCompound.getTagMap(this.theNBTTagCompound).get(this.field_82585_a)).getId()];
    }

    @Override
    public String call() {
        return this.func_82583_a();
    }
}
