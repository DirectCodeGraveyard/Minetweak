package net.minecraft.utils.callable;

import net.minecraft.world.map.MapGenStructure;

import java.util.concurrent.Callable;

public class CallableStructureType implements Callable<String> {
    final MapGenStructure theMapStructureGenerator;

    public CallableStructureType(MapGenStructure par1MapGenStructure) {
        this.theMapStructureGenerator = par1MapGenStructure;
    }

    public String callStructureType() {
        return this.theMapStructureGenerator.getClass().getCanonicalName();
    }

    @Override
    public String call() {
        return this.callStructureType();
    }
}
