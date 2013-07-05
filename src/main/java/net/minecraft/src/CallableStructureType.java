package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableStructureType implements Callable<String> {
    final MapGenStructure theMapStructureGenerator;

    CallableStructureType(MapGenStructure par1MapGenStructure) {
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
