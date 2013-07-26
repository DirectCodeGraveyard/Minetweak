package net.minecraft.src;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

public class RegistrySimple implements IRegistry {
    /**
     * Objects registered on this registry.
     */
    protected final Map<Object, Object> registryObjects = this.func_111054_a();

    protected HashMap<Object, Object> func_111054_a() {
        return Maps.newHashMap();
    }

    @Override
    public Object func_82594_a(Object par1Obj) {
        return this.registryObjects.get(par1Obj);
    }

    /**
     * Register an object on this registry.
     */
    @Override
    public void putObject(Object par1Obj, Object par2Obj) {
        this.registryObjects.put(par1Obj, par2Obj);
    }
}
